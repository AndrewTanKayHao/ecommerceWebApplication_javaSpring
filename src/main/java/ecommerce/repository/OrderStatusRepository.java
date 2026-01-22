package ecommerce.repository;

import ecommerce.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

//Author: Angeline

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByStatus(String status);
}
