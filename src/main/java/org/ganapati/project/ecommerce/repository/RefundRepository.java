package org.ganapati.project.ecommerce.repository;

import org.ganapati.project.ecommerce.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    Optional<Refund> findByOrderItem_Id(Long orderItemId);

}
