package org.ganapati.project.ecommerce.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.ganapati.project.ecommerce.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder_Id(Long orderId);

    @Query("select p from Payment p where p.order.user.id=:userId")
    Page<Payment> findPaymentHistoryByUserId(@Param("userId") Long userId, Pageable pageable);

}

