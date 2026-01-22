package ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.interfacemethods.OrderDetailInterface;
import ecommerce.model.CartItem;
import ecommerce.model.OrderDetail;
import ecommerce.repository.OrderDetailRepository;
import jakarta.transaction.Transactional;

//Author: Dang Lam

@Service
@Transactional
public class OrderDetailImplementation implements OrderDetailInterface {
	
	@Autowired
	OrderDetailRepository orderDetailRepo;
	
	@Override
	public void save(OrderDetail orderDetail) {
		orderDetailRepo.save(orderDetail);
	}
	
}
