package ecommerce.interfacemethods;

import ecommerce.model.Cart;
import ecommerce.model.Order;

// Author: Dang Lam

public interface CheckoutInterface {
	Order reserveOrder(Cart cart);
	boolean checkEnoughProduct(Cart cart);
}
