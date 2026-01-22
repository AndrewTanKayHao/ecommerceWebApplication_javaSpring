package ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.model.OrderDetail;

//Author: Dang Lam

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

}
