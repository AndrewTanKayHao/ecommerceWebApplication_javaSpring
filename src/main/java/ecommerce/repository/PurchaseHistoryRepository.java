package ecommerce.repository;

import ecommerce.model.Order;
import ecommerce.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

//Author: Nyunt

public interface PurchaseHistoryRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.customer.id = :customerId")
    List<Order> findOrderById(@Param("orderId") long orderId, @Param("customerId") long customerId);

    @Query("SELECT o FROM Order o WHERE o.dateTime BETWEEN :startOfDay AND :endOfDay AND o.customer.id = :customerId")
    List<Order> findOrderByOrderDate(@Param("startOfDay") ZonedDateTime startOfDay,
                                     @Param("endOfDay")  ZonedDateTime endOfDay,
                                     @Param("customerId") long customerId);

    @Query("SELECT o FROM Order o JOIN o.status s WHERE LOWER(s.status) LIKE LOWER(CONCAT('%',:state ,'%')) AND o.customer.id = :customerId")
    List<Order> findOrderByStatus(@Param("state") String status, @Param("customerId") long customerId);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findOrderByCustomerId(@Param("customerId") long customerId);
    @Query("SELECT s FROM OrderStatus s WHERE LOWER(s.status) = LOWER(:statusName)")
    OrderStatus findOrderStatusByName(@Param("statusName") String statusName);
}
