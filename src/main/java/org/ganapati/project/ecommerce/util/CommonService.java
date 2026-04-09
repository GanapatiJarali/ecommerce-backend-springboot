package org.ganapati.project.ecommerce.util;

import lombok.extern.slf4j.Slf4j;

import org.ganapati.project.ecommerce.exception.ValidationException;

import org.ganapati.project.ecommerce.entity.*;
import org.ganapati.project.ecommerce.repository.*;
import org.ganapati.project.ecommerce.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommonService {

    private final UserRepository userRepository;


    private final ProductRepository productRepository;


    private final CartRepository cartRepository;


    private final AddressRepository addressRepository;


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final CacheService cacheService;

    @Autowired
    public CommonService(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, AddressRepository addressRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, CacheService cacheService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cacheService = cacheService;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ValidationException(1005, "user not found..!", "user not found..!"));
    }


    public void userAlreadyExist(String email) {
        log.info("user fetching from DB: {} ", email);
        User user = cacheService.findUserByEmail(email);
        if (!ObjectUtils.isEmpty(user)) {
            throw new ValidationException(1005, "user already  found..!", "user already found..!");
        }
    }

    @CachePut(value = "user", key = "#user.email", unless = "#result==null")
    public User updateUser(User user) {
        log.info("user update:{} ", user);
        return cacheService.updateUser(user);
    }

    public Product findProductByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ValidationException(2001, "Product Not found..!", "Product Not found..!"));
    }

    public Product findByProductNameAndStatus(String productName, boolean status) {
        return productRepository.findByNameAndStatus(productName.toLowerCase(), status).orElseThrow(() -> new ValidationException(2001, "Product Not found..!", "Product Not found..!"));
    }

    public Optional<Cart> fetchProductWithUserCart(User user, Product product) {
        return cartRepository
                .findByUserAndProductAndStatus(user, product, true);
    }

    public Address findAddressByAddressId(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new ValidationException(4000, "Address not found..!", "Address not found..!"));
    }

    public Optional<Order> fetchOrderByOrderIdAndUser(String orderId, User user) {
        return orderRepository.findByOrderGroupIdAndUser(orderId, user);
    }

    public Optional<Order> fetchOrderByOrderId(String orderId) {
        return orderRepository.findByOrderGroupId(orderId);
    }

    public void roleAccessValidation(List<String> apiRoleAccess, List<String> rolesJwtToken) {
        boolean rolesMatched = apiRoleAccess.stream().anyMatch(rolesJwtToken::contains);
        if (!rolesMatched) {
            throw new ValidationException(1013, "UnAuthorization for operation..!", "UnAuthorization for operation");
        }
    }

    public OrderItem fetchByOrderItemId(String orderItemId) {
        return orderItemRepository.findByOrderItemId(orderItemId).orElseThrow(() -> new ValidationException(6001, "OrderItem not found..! ", "OrderItem not found..! "));
    }

    public Order fetchByOrderId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ValidationException(6001, "OrderItem not found..! ", "OrderItem not found..! "));
    }

    public List<Order> fetchOrderByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }
}
