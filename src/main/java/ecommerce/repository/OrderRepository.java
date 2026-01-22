package ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.model.Order;
import jakarta.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order,Long> {

	//Author: Dang Lam
	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.paymentMethod = :paymentMethod WHERE o.id = :id")
    void updateOrderPaymentMethod(@Param("id") long id, @Param("paymentMethod") String paymentMethod);

	//Author: Angeline
	@Query("SELECT COUNT(o) FROM Order o JOIN o.orderDetailList od WHERE od.product.id = :productId AND LOWER(o.status.status) = 'pending'")
	long countPendingOrdersByProductId(@Param("productId") Long productId);

}
