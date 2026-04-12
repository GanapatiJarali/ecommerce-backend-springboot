import org.ganapati.project.ecommerce.entity.*;
import org.ganapati.project.ecommerce.enums.AddressType;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import org.ganapati.project.ecommerce.enums.RoleType;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.repository.*;
import org.ganapati.project.ecommerce.service.CacheService;
import org.ganapati.project.ecommerce.util.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommonService commonService;
    @Mock
    private CacheService cacheService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    private User user;
    private Product product;
    private Cart cart;
    private Category category;
    private Address address;
    private Order order;
    private Order order1;
    private List<Order> orderList;
    private OrderItem orderItem;
    private Map<String, List<String>> jwtRoles;

    @BeforeEach
    public void beforeAll() {
        user = new User();
        user.setId(1L);
        user.setMobileNo("9632580477");
        user.setName("Ganapati Jarali");
        user.setEmail("ganapatijarali4126@gmail.com");
        user.setPassword("Welcome@1234");
        user.setGender("Male");
        user.setDateOfBirth(LocalDate.of(1999, 12, 24));
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now().plusDays(10));
        user.setCreatedBy("USER");
        user.setUpdateBy("USER");

        category = new Category();
        category.setId(1L);
        category.setName("Fashion");
        category.setStatus(true);
        category.setCreateAt(LocalDateTime.now());
        category.setUpdateAt(LocalDateTime.now().plusDays(10));
        category.setCreatedBy("ADMIN");
        category.setUpdatedBy("ADMIN");

        product = new Product();
        product.setId(1L);
        product.setName("Allen solly T-shirts:");
        product.setPrice(10.00);
        product.setProductCode("ASTS");
        product.setStock(10);
        product.setStatus(true);
        product.setCategory(category);

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setQuantity(10);
        cart.setPrice(new BigDecimal("10000.00"));
        cart.setStatus(true);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        address = new Address();
        address.setId(1L);
        address.setFullName("Ganapati");
        address.setMobileNo("9632580477");
        address.setAddressLine1("Btm layout Bangalore");
        address.setAddressLine2("Btm layout Bangalore");
        address.setCity("Bengaluru");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setLandMark("postOffice");
        address.setPinCode("591313");
        address.setAddressType(AddressType.SHIPPING);
        address.setDefault(true);
        address.setUser(user);

        order = new Order();
        order.setId(2L);
        order.setUser(user);
        order.setOrderGroupId("ECOM-GRP-ORD-2IUSUAAKKA-AJJA");
        order.setAddress(address);
        order.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setPaymentMode("UPI");

        List<String> roleTypes = Arrays.asList(RoleType.SUPER_ADMIN.toString(), RoleType.ADMIN.toString());
        jwtRoles = new HashMap<>();
        jwtRoles.put("roles", roleTypes);

        orderItem = new OrderItem();
        orderItem.setId(2L);
        orderItem.setOrderId(order.getId());
        orderItem.setProduct(product);
        orderItem.setOrderItemId("ECOM-ORD--7281");
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("1020"));
        orderItem.setTotalPrice(new BigDecimal("2040"));
        orderItem.setReason(null);
        orderItem.setStatus(OrderItemStatus.DELIVERED);

        order1 = new Order();
        order1.setId(2L);
        order1.setUser(user);
        order1.setOrderGroupId("ECOM-GRP-ORD-2IUSUAAKKA-AJJA");
        order1.setAddress(address);
        order1.setPaymentStatus(PaymentStatus.SUCCESS);
        order1.setPaymentMode("UPI");

        orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order1);
    }

    @Test
    public void findByEmailTest() {
        String email = "ganapatijarali4126@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User result = commonService.findByEmail(email);
        assertEquals(result, user);
    }

    @Test
    public void updateUserTest() {
        when(cacheService.updateUser(user)).thenReturn(user);
        User user1 = commonService.updateUser(user);
        assertEquals(user1, user);
    }

    @Test
    public void findProductByProductIdTest() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = commonService.findProductByProductId(1L);
        assertEquals(result, product);

        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> commonService.findProductByProductId(2L));
        assertEquals(2001, exception.getErrorCode());
    }

    @Test
    public void findByProductNameAndStatusTest() {
        when(productRepository.findByNameAndStatus("mobile", true)).thenReturn(Optional.of(product));
        Product result = commonService.findByProductNameAndStatus("mobile", true);
        assertEquals(result, product);

        when(productRepository.findByNameAndStatus("mobile", true)).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> commonService.findByProductNameAndStatus("mobile", true));
        assertEquals(2002, exception.getErrorCode());
    }

    @Test
    public void fetchProductWithUserCartTest() {
        when(cartRepository.findByUserAndProductAndStatus(user, product, true)).thenReturn(Optional.of(cart));
        Optional<Cart> cartResult = commonService.fetchProductWithUserCart(user, product);
        assertEquals(cartResult.get(), cart);
    }

    @Test
    public void findAddressByAddressId() {
        when(addressRepository.findById(2L)).thenReturn(Optional.of(address));
        Address addressResult = commonService.findAddressByAddressId(2l);
        assertEquals(addressResult, address);

        when(addressRepository.findById(2L)).thenReturn(Optional.empty());
        ValidationException validationException = assertThrows(ValidationException.class, () -> commonService.findAddressByAddressId(2L));
        assertEquals(4000, validationException.getErrorCode());
    }

    @Test
    public void fetchOrderByOrderGroupIdAndUserTest() {
        when(orderRepository.findByOrderGroupIdAndUser(order.getOrderGroupId(), user)).thenReturn(Optional.of(order));
        Optional<Order> orderResult = orderRepository.findByOrderGroupIdAndUser(order.getOrderGroupId(), user);
        assertEquals(orderResult, Optional.of(order));
    }

    @Test
    public void fetchOrderByOrderIdTest() {
        when(orderRepository.findByOrderGroupId(order.getOrderGroupId())).thenReturn(Optional.of(order));
        Optional<Order> orderResult = commonService.fetchOrderByOrderId(order.getOrderGroupId());
        assertEquals(orderResult.get(), order);
    }

    @Test
    public void roleAccessValidationSuccessTest() {
        List<String> apiRoleAccess = List.of(RoleType.ADMIN.toString(), RoleType.SUPER_ADMIN.toString());
        commonService.roleAccessValidation(apiRoleAccess, jwtRoles.get("roles"));
    }

    @Test
    public void roleAccessValidationFailedTest() {
        List<String> apiRoleAccess = List.of(RoleType.USER.toString());
        ValidationException validationException = assertThrows(ValidationException.class, () -> commonService.roleAccessValidation(apiRoleAccess, jwtRoles.get("roles")));
        assertEquals(1013, validationException.getErrorCode());
    }

    @Test
    public void fetchByOrderItemIdTest() {
        when(orderItemRepository.findByOrderItemId(orderItem.getOrderItemId())).thenReturn(Optional.of(orderItem));
        OrderItem orderItemResult = commonService.fetchByOrderItemId(orderItem.getOrderItemId());
        assertEquals(orderItemResult, orderItem);
        when(orderItemRepository.findByOrderItemId(orderItem.getOrderItemId())).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> commonService.fetchByOrderItemId(orderItem.getOrderItemId()));
        assertEquals(6001, exception.getErrorCode());
    }

    @Test
    public void fetchByOrderIdTest() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Order orderResult = commonService.fetchByOrderId(order.getId());
        assertEquals(orderResult, order);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> commonService.fetchByOrderId(order.getId()));
        assertEquals(6001, exception.getErrorCode());
    }

    @Test
    public void fetchOrderByUserIdTest() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        when(orderRepository.findByUser_Id(user.getId())).thenReturn(orders);
        List<Order> orderResults = commonService.fetchOrderByUserId(user.getId());
        assertEquals(orderResults, orderList);
    }

    @Test
    public void fetchOrderByOrderGroupIdTest() {
        when(orderRepository.findByOrderGroupId(order.getOrderGroupId())).thenReturn(Optional.of(order));
        Optional<Order> orderResult = commonService.fetchOrderByOrderGroupId(order.getOrderGroupId());
        assertEquals(orderResult, Optional.of(order));
    }
}
