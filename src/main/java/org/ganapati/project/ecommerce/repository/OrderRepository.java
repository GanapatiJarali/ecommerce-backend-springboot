package org.ganapati.project.ecommerce.repository;

import org.ganapati.project.ecommerce.entity.Order;
import org.ganapati.project.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Id(Long userId, Pageable pageable);

    List<Order> findByUser_Id(Long userId);

    Optional<Order> findByOrderGroupIdAndUser(String orderId, User user);

    Optional<Order> findByOrderGroupId(String orderId);
}
