package ecommerce.interfacemethods;

import java.util.ArrayList;

import ecommerce.model.Cart;
import ecommerce.model.Customer;
import ecommerce.model.Product;

// Author: Andrew

public interface CartInterface {
	public Cart createCart(Customer customer);
	public boolean updateCartQuantity(Cart cart, Product product, int orderedQuantity);
	public boolean removeCartProduct(Cart cart, Product product);
	public boolean addCartProduct(Cart cart, Product product, int orderedQuantity);
	public boolean clearCart(Cart cart);
	
	//This method will update the new Total Price of a cart's product list. It should be called from inside the update/remove/add methods.
	public boolean updateCartTotal(Cart cart);
	long getCartItemQuantity(Cart cart, long productId);
	boolean decreaseCartProduct(Cart cart, Product product, int orderedQuantity);
	boolean increaseCartProduct(Cart cart, Product product, int orderedQuantity);
	boolean updateCartDeliveryOption(Cart sessionCart, int deliveryOption);
	boolean addCartDeliveryFee(Cart sessionCart);
}
