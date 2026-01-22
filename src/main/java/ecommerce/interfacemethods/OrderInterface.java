package ecommerce.interfacemethods;

import ecommerce.model.Cart;
import ecommerce.model.Order;

//Author: Dang Lam
public interface OrderInterface {

	void save(Order order);
	Order converCartToOrder(Cart cart);
	void saveOrderAndProductQuantity(Order order);
	void setOrderPaymentMethod(Order order, String paymentMethod);
	void rollbackFailOrder(Order order);
	void deleteOrder(long id);
	Order getOrderById(long id);
}
