package ecommerce.service;

import ecommerce.model.OrderStatus;
import ecommerce.model.Product;
import ecommerce.repository.OrderStatusRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.ProductInterface;
import ecommerce.model.Cart;
import ecommerce.model.Order;
import ecommerce.model.OrderDetail;
import ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;

//Author: Dang Lam

@Service
public class OrderImplementation implements OrderInterface {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	OrderStatusRepository orderStatusRepo;
	
	@Autowired
	ProductInterface productService;
	
	@Override
	public void save(Order order) {
		orderRepo.save(order);
		System.out.println("Order saved successfully!");
	}

	@Override
	public Order converCartToOrder(Cart cart) {
		OrderStatus pending = orderStatusRepo.findByStatus("Pending");
		Order newOrder = new Order(cart, pending);
		System.out.println("Order creation successful!");
		System.out.println(newOrder);
		return newOrder;
	}
	
	@Transactional
	@Override
	public void setOrderPaymentMethod(Order order, String paymentMethod) {
		orderRepo.updateOrderPaymentMethod(order.getId(), paymentMethod);
	}
	
	@Transactional
	@Override
	public void saveOrderAndProductQuantity(Order order) {
		for(OrderDetail orderDetail: order.getOrderDetailList()) {
			Product product = orderDetail.getProduct();
			int productQuantity = product.getStockQuantity();
			if(productQuantity < orderDetail.getOrderedQuantity()) {
				throw new RuntimeException(
	                    "Not enough stock for product: " + product.getName()
	            );
			}
			productService.updateProductQuantity(product.getId(), productQuantity-orderDetail.getOrderedQuantity());
			
		}
		save(order);
	}
	
	@Transactional
	@Override
	public void rollbackFailOrder(Order order) {
		List<OrderDetail> details = new ArrayList<>(order.getOrderDetailList());
		
		for(OrderDetail orderDetail: details) {
			Product product = orderDetail.getProduct();
			int newQuantity = product.getStockQuantity() + orderDetail.getOrderedQuantity();
			productService.updateProductQuantity(product.getId(), newQuantity);
		}
		deleteOrder(order.getId());
	}
	
	@Transactional
	@Override
	public void deleteOrder(long id) {
		orderRepo.deleteById(id);
	}
	
	@Override
	public Order getOrderById(long id) {
	    return orderRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
	}
}
