package org.ganapati.project.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.common.CommonConstants;
import org.ganapati.project.ecommerce.config.JwtRequestContext;
import org.ganapati.project.ecommerce.dto.*;
import org.ganapati.project.ecommerce.entity.*;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.mapper.OrderItemMapper;
import org.ganapati.project.ecommerce.mapper.OrderMapper;
import org.ganapati.project.ecommerce.repository.OrderItemRepository;
import org.ganapati.project.ecommerce.repository.OrderRepository;
import org.ganapati.project.ecommerce.repository.ProductRepository;
import org.ganapati.project.ecommerce.util.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JwtRequestContext jwtRequestContext;

    @Autowired
    private CommonService commonService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Value("${order.update.status.roles}")
    private List<String> orderUpdateStatusRole;
    @Value("${order.history.status}")
    private List<String> orderHistoryStatus;


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BaseResponse<OrderResponse> placeOrder(OrderRequest orderRequest) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        //Validation correct customer and correct user
        Address address = commonService.findAddressByAddressId(orderRequest.getAddressId());
        //  Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        Map<Long, Product> productMap = new HashMap<>();
        for (OrderItemRequest item : orderRequest.getItems()) {
            Product product = commonService.findProductByProductId(item.getProductId());
            //checking before order ,the quantity of product is available in the ecommerce
            if (!(product.getStock() >= item.getQuantity())) {
                throw new ValidationException(7000, "Product: " + product.getName() + " Not Enough Quantity: " + item.getQuantity() + " To Create Order:", "Product: " + product.getName() + " Not enough Quantity:" + item.getQuantity() + " To Create Order:");
            }
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            totalAmount = totalAmount.add(price.multiply(quantity));
            productMap.put(product.getId(), product);
        }
        //  Create Order
        Order order = new Order();
        order.setOrderGroupId(generateGroupOrderId());
        order.setUser(user);
        order.setPaymentMode(orderRequest.getPaymentMode());
        order.setAddress(address);
        order.setTotalAmount(totalAmount);

        order.setPaymentStatus(PaymentStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItemList = new ArrayList<>();
        //  Create Order Items
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for (OrderItemRequest item : orderRequest.getItems()) {

//          Product product = commonService.findProductByProductId(item.getProductId());
            Product product = productMap.get(item.getProductId());
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal itemTotal = price.multiply(quantity);

            OrderItem orderItem = new OrderItem();
            //orderId=ECOM-ORD-YYYYMMDD-XXXXXX
            orderItem.setOrderItemId(generateOrderId());
            orderItem.setStatus(OrderItemStatus.CREATED);
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(price);
            orderItem.setTotalPrice(itemTotal);

            orderItemList.add(orderItem);
            product.setStock(product.getStock() - item.getQuantity());
//            productList.add(product);
            productMap.put(item.getProductId(), product);
            OrderItemResponse response = new OrderItemResponse();
            response.setOrderItemId(orderItem.getOrderItemId());
            response.setStatus(orderItem.getStatus());
            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(item.getQuantity());
            response.setPrice(price);
            response.setTotalPrice(itemTotal);
            orderItemResponses.add(response);
        }

        orderItemRepository.saveAll(orderItemList);
        productRepository.saveAll(productMap.values());
        //  Prepare Response
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setTotalAmount(savedOrder.getTotalAmount());
        orderResponse.setPaymentStatus(savedOrder.getPaymentStatus());
        orderResponse.setOrderDate(savedOrder.getCreatedAt());
        orderResponse.setOrderGroupId(savedOrder.getOrderGroupId());
        orderResponse.setItems(orderItemResponses);
        return BaseResponse.success(orderResponse);
    }


    @Override
    public BaseResponse<PageResponse<OrderRes>> getOrdersByUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        log.info("getOrderBy email: {} ,", email);
        Page<Order> ordersPage = orderRepository.findByUser_Id(user.getId(), pageable);
        PageResponse<OrderRes> orderResPageResponse = new PageResponse<>();
        List<OrderRes> orderResponse = ordersPage.getContent().stream().map(orderMapper::entityToOrderRes).toList();
        log.info("responseOrders:   {}", orderResponse);
        orderResPageResponse.setData(orderResponse);
        orderResPageResponse.setTotalPages(ordersPage.getTotalPages());
        orderResPageResponse.setTotalElements(ordersPage.getTotalElements());
        return BaseResponse.success(orderResPageResponse);
    }

    @Override
    public BaseResponse<OrderResponse> getOrdersById(String orderGroupId) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        Order order = commonService.fetchOrderByOrderIdAndUser(orderGroupId, user).orElseThrow(() -> new ValidationException(5010, "Order Not Found ", "Order Not Found "));
        log.info("order: {} ", order);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderGroupId(order.getOrderGroupId());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setPaymentStatus(order.getPaymentStatus());
        orderResponse.setPaymentMode(order.getPaymentMode());
        orderResponse.setOrderDate(order.getCreatedAt());
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        orderResponse.setItems(orderItems.stream().map(res -> orderItemMapper.entityToResponse(res)).toList());
        return BaseResponse.success(orderResponse);
    }

    @Override
    public BaseResponse<OrderItemResponse> fetchOrderItemId(String orderItemId) {
        OrderItem orderItemRes = orderItemRepository.findByOrderItemId(orderItemId).orElseThrow(() -> new ValidationException(6000, "orderItemID not Found..!", "orderItemID not found...!"));
        log.info("orderItemRes: {}", orderItemRes);
        return BaseResponse.success(orderItemMapper.entityToResponse(orderItemRes));
    }

    @Override
    public BaseResponse<List<OrderItem>> fetchOrderHistoryStatusByUser(String status) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        OrderItemStatus orderStatus;
        try {
            orderStatus = OrderItemStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ValidationException(400, "Invalid status", "Invalid status");
        }
        List<Long> orderIds = commonService.fetchOrderByUserId(user.getId()).stream().map(Order::getId).toList();
        if (orderIds.isEmpty()) {
            return BaseResponse.success(Collections.emptyList());
        }
        List<OrderItem> orderItems = orderItemRepository.findByOrderIds(orderIds, orderStatus);
        return BaseResponse.success(orderItems);
    }

    @Override
    public BaseResponse<PageResponse<OrderItemResponse>> fetchOrderHistoryStatus(String status, int page, int size) {
        List<String> jwtRoles = jwtRequestContext.getRoles();
        commonService.roleAccessValidation(orderHistoryStatus, jwtRoles);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        OrderItemStatus orderStatus;
        try {
            orderStatus = OrderItemStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ValidationException(400, "Invalid status", "Invalid status");
        }
        Page<OrderItem> pageResult = orderItemRepository.findByStatus(orderStatus, pageable);
        log.info("Fetched {} orderItems with status {}", pageResult.getNumberOfElements(), status);
        PageResponse<OrderItemResponse> response = new PageResponse<>();
        response.setTotalPages(pageResult.getTotalPages());
        response.setTotalElements(pageResult.getTotalElements());
        response.setData(orderItemMapper.entityToResponseList(pageResult.getContent()));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<OrderItemResponse> cancelOrder(String orderItemId) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);

        OrderItem orderItem = orderItemRepository.findByOrderItemId(orderItemId)
                .orElseThrow(() -> new ValidationException(5010, "Order itemId Not Found", "Order itemId Not Found"));
        Order order = orderRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new ValidationException(5011, "Order not found", "Order not found"));
        // Ownership check order.
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ValidationException(9001, "Unauthorized", "Unauthorized");
        }
        // Already cancelled throw error
        if (OrderItemStatus.CANCELLED == orderItem.getStatus()) {
            throw new ValidationException(5011, "Already cancelled", "Already cancelled");
        }
        // Cannot cancel after shipped the order
        if (orderItem.getStatus() == OrderItemStatus.SHIPPED ||
                orderItem.getStatus() == OrderItemStatus.DELIVERED) {
            throw new ValidationException(5013, "Cannot cancel after shipping", "Cannot cancel after shipping");
        }

        // Refund only if paid order
        if (PaymentStatus.SUCCESS.equals(order.getPaymentStatus())) {
            // TODO: trigger refund
        }
        // Update status
        orderItem.setStatus(OrderItemStatus.CANCELLED);
        // Restore stock
        Product product = orderItem.getProduct();
        product.setStock(product.getStock() + orderItem.getQuantity());
        productRepository.save(product);
        OrderItem saved = orderItemRepository.save(orderItem);
        return BaseResponse.success(orderItemMapper.entityToResponse(saved));
    }

    @Override
    public BaseResponse updateOrderStatus(String orderItemId, OrderUpdateStatusRequest request) {
        List<String> jwtRoles = jwtRequestContext.getRoles();
        commonService.roleAccessValidation(orderUpdateStatusRole, jwtRoles);

        OrderItem orderItem = commonService.fetchByOrderItemId(orderItemId);
        Order order = commonService.fetchByOrderId(orderItem.getOrderId());

        OrderItemStatus requestedStatus;
        try {
            requestedStatus = OrderItemStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(8000, "Invalid order status", "Invalid order status");
        }
        log.info("Updating orderItem {} from {} to {}", orderItemId, orderItem.getStatus(), requestedStatus);

        //  Cannot update cancelled
        if (orderItem.getStatus() == OrderItemStatus.CANCELLED) {
            throw new ValidationException(8006, "Cancelled order cannot be updated", "Cancelled order cannot be updated");
        }

        // DELIVERED
        if (requestedStatus == OrderItemStatus.DELIVERED) {

            if (orderItem.getStatus() == OrderItemStatus.DELIVERED) {
                throw new ValidationException(8001, "Already delivered", "Already delivered");
            }

            if (orderItem.getStatus() != OrderItemStatus.SHIPPED) {
                throw new ValidationException(8001, "Not in shipped state", "Not in shipped state");
            }

            if (order.getPaymentStatus() != PaymentStatus.SUCCESS) {
                throw new ValidationException(8001, "Payment not completed", "Payment not completed");
            }

        }
        // shipped
        else if (requestedStatus == OrderItemStatus.SHIPPED) {

            if (orderItem.getStatus() != OrderItemStatus.CREATED) {
                throw new ValidationException(8002, "Not in created state", "Not in created state");
            }
        }
        // returned
        else if (requestedStatus == OrderItemStatus.RETURNED) {

            if (orderItem.getStatus() != OrderItemStatus.RETURN_REQUESTED) {
                throw new ValidationException(8004, "Return not requested", "Return not requested");
            }
        } else {
            throw new ValidationException(8005, "Invalid state transition", "Invalid state transition");
        }
        orderItem.setStatus(requestedStatus);
        orderItemRepository.save(orderItem);
        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<OrderReturnResponse> orderReturn(String orderItemId, OrderReturnRequest orderRequest) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        log.info("User {} requesting return for orderItem {}", email, orderItemId);

        OrderItem orderItem = commonService.fetchByOrderItemId(orderItemId);
        Order order = commonService.fetchByOrderId(orderItem.getOrderId());

        // security check order belongs to user.
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ValidationException(403, "Unauthorized", "Unauthorized");
        }

        // Already requested
        if (orderItem.getStatus() == OrderItemStatus.RETURN_REQUESTED) {
            throw new ValidationException(7001, "Already requested for return", "Already requested for return");
        }
        // Must be delivered
        if (orderItem.getStatus() != OrderItemStatus.DELIVERED) {
            throw new ValidationException(8087, "Return allowed only after delivery", "Return allowed only after delivery");
        }
        // Update status with return requested..!
        orderItem.setStatus(OrderItemStatus.RETURN_REQUESTED);
        orderItem.setReason(orderRequest.getReason());
        orderItemRepository.save(orderItem);
        OrderReturnResponse response = new OrderReturnResponse();
        response.setOrderId(orderItemId);
        response.setOrderItemStatus(OrderItemStatus.RETURN_REQUESTED);
        response.setReason(orderRequest.getReason());
        return BaseResponse.success(response);
    }

    public String generateOrderId() {
        //year-month -date
        String date = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return CommonConstants.ORDER_ID_INITIAL + CommonConstants.HYPEN + date + CommonConstants.HYPEN + randomUuid;
    }

    public String generateGroupOrderId() {
        //year-month -date
        String date = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return CommonConstants.GROUP_ORDER_ID_INITIAL + CommonConstants.HYPEN + date + CommonConstants.HYPEN + randomUuid;
    }
}
