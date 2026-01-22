package ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.PaymentInterface;
import ecommerce.model.Order;

//Author: Dang Lam 
@Service("codPaymentService")
public class PaymentCodImplementation implements PaymentInterface{
	
	@Autowired
	OrderInterface orderService;
	
	private String name = "Cash On Delivery";
	
	
	@Override
	public String getPaymentMethodName() {
		return name;
	}
	
	@Override
	public String processPayment(Order order) throws Exception {
		orderService.setOrderPaymentMethod(order, this.name);
		return "/success?orderId=" + order.getId();
	}
}
