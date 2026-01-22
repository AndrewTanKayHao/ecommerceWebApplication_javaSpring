package ecommerce.interfacemethods;

import ecommerce.model.Order;

//Author: Danglam 
public interface PaymentInterface {
	String getPaymentMethodName();
	String processPayment(Order order) throws Exception;
}
