package org.ganapati.project.ecommerce.repository;

import io.lettuce.core.dynamic.annotation.Param;
import io.lettuce.core.output.ListOfGenericMapsOutput;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderItemId(String orderItemId);

    List<OrderItem> findByOrderId(Long orderId);

    @Query("select oi from OrderItem oi where oi.status=:status ")
    Page<OrderItem> findByStatus(@Param("status") OrderItemStatus status, Pageable pageable);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId IN :orderIds AND oi.status = :status")
    List<OrderItem> findByOrderIds(@Param("orderIds") List<Long> orderIds,
                                   @Param("status") OrderItemStatus status);
}
