package ecommerce.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import ecommerce.interfacemethods.CartInterface;
import ecommerce.model.Cart;
import ecommerce.model.CartItem;
import ecommerce.model.Customer;
import ecommerce.model.Product;
import jakarta.transaction.Transactional;

//Author: Andrew

@Service
@Transactional
public class CartImplementation implements CartInterface{
	
	@Override
	public Cart createCart(Customer customer) {
		Cart cart = new Cart(customer);
		System.out.println("Cart creation successful!");
		return cart;
	}
	
	@Override
	public boolean updateCartQuantity(Cart cart, Product product, int orderedQuantity) {
		try {
			for(CartItem cartItem : cart.getCartItemList()) {
				if (cartItem.getProduct().getId()==product.getId()) {
					cartItem.setOrderedQuantity(orderedQuantity);
					if (updateCartTotal(cart)==true) {
						System.out.println("Update Cart Quantity successful!");
						return true;
						}
					else {
						System.out.println("Update Cart Total unsuccessful.");
						return false;
						}
					}
				}
			}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
		System.out.println("Error: Update Cart Quantity unsuccessful.");
		return false;
	}
	
	@Override
	public boolean increaseCartProduct(Cart cart, Product product, int orderedQuantity) {
		try {
			for(CartItem cartItem : cart.getCartItemList()) {
				if (cartItem.getProduct().getId()==product.getId()) {
					cartItem.setOrderedQuantity(cartItem.getOrderedQuantity()+orderedQuantity);
					if (updateCartTotal(cart)==true) {
						System.out.println("Increased " + product.getName() + " quantity in cart by 1.");
						return true;
						}
					else {
						System.out.println("Update Cart Total unsuccessful.");
						return false;
						}
					}
				}
			}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
		System.out.println("Error: Increase Cart Product unsuccessful.");
		return false;
	}
	
	@Override
	public boolean decreaseCartProduct(Cart cart, Product product, int orderedQuantity) {
		try {
			for(CartItem cartItem : cart.getCartItemList()) {
				if (cartItem.getProduct().getId()==product.getId() && (cartItem.getOrderedQuantity()-1 >= 0)) {
					cartItem.setOrderedQuantity(cartItem.getOrderedQuantity()-orderedQuantity);
					if (updateCartTotal(cart)==true) {
						System.out.println("Decreased " + product.getName() + " quantity in cart by 1.");
						if(cartItem.getOrderedQuantity()==0) {
							cart.getCartItemList().remove(cartItem);
						}			
						return true;
						}
					else {
						System.out.println("Update Cart Total unsuccessful.");
						return false;
						}
					}
				}
			}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
		System.out.println("Error: Decrease Cart Product unsuccessful.");
		return false;
	}
	
	@Override
	public boolean removeCartProduct(Cart cart, Product product) {
		try {
			for(CartItem cartItem : cart.getCartItemList()) {
				if (cartItem.getProduct().getId()==product.getId()) {
					cart.getCartItemList().remove(cartItem);
					if (updateCartTotal(cart)==true) {
						System.out.println("Removed " + product.getName() + " from Cart successfully!");
						return true;
						}
					else {
						System.out.println("Update Cart Total unsuccessful.");
						return false;
						}
					}
				}
			}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
		System.out.println("Error: Remove Cart Product unsuccessful.");
		return false;
	}

	@Override
	public boolean addCartProduct(Cart cart, Product product, int orderedQuantity) {
		try {
			boolean inCart = false;
			for(CartItem cartItem: cart.getCartItemList()) {
				if (cartItem.getProduct().getId()==product.getId()) {
					inCart = true;
					}
				}
			if (inCart==false){
				cart.getCartItemList().add(new CartItem(product, orderedQuantity));
				if (updateCartTotal(cart)==true) {
				System.out.println("Added " + product.getName() + " to Cart successfully!");
				return true;
				}
				else {
					System.out.println("Update Cart Total unsuccessful.");
					return false;
				}
			}
			else {
				System.out.println("Error: Add Cart Product unsuccessful.");
				return false;
			}
		}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
	}

	@Override
	public boolean clearCart(Cart cart) {
		cart.getCartItemList().clear();
		cart.setCartTotal(BigDecimal.ZERO);
		return false;
	}

	//This method will update the new Total Price of a cart's product list. It is called from inside the update/remove/add methods.
	@Override
	public boolean updateCartTotal(Cart cart) {
		try{BigDecimal newCartTotal=BigDecimal.ZERO;
		for (CartItem cartItem : cart.getCartItemList()) {
			newCartTotal=newCartTotal.add(cartItem.getProduct().getUnitPrice().multiply(new BigDecimal(cartItem.getOrderedQuantity())).setScale(2, RoundingMode.HALF_UP));
		}
		cart.setCartTotal(newCartTotal);
		System.out.println("Update Cart Total successful! " + newCartTotal);
		
		return true;
		}
		catch (Exception e) {
			System.out.println("Exception encountered.");
			return false;
		}
	}
	
	@Override
	public long getCartItemQuantity(Cart cart, long productId) {
	for (CartItem cartItem : cart.getCartItemList()) {
		if (cartItem.getProduct().getId() == productId) {
			System.out.println("Retrieve Ordered Quantity successful!");
			return cartItem.getOrderedQuantity();
		}
	}
	System.out.println("Error, Product ID: " + productId + " not found.");
	return 0;
	}
	
	@Override
	public boolean addCartDeliveryFee(Cart sessionCart) {
		int deliveryOption = sessionCart.getDeliveryOption();
		try{
			if(deliveryOption==1) {
				sessionCart.setCartTotal(sessionCart.getCartTotal().add(new BigDecimal(10)));
				System.out.println("Added delivery fee $10 to Cart Total successfully! " + sessionCart.getCartTotal());
				return true;
			}
			else if(deliveryOption==2){
				sessionCart.setCartTotal(sessionCart.getCartTotal().add(new BigDecimal(18)));
				System.out.println("Added delivery fee $18 to Cart Total successfully! " + sessionCart.getCartTotal());
				return true;
			}
			else if (deliveryOption==3) {
				sessionCart.setCartTotal(sessionCart.getCartTotal());
				System.out.println("Added delivery fee $0 to Cart Total successfully!" + sessionCart.getCartTotal());
				return true;
			}
		}catch(Exception e) {
		System.out.println("Exception encountered.");
		return false;
		}		
		return false;
	}
	
	@Override
	public boolean updateCartDeliveryOption(Cart sessionCart, int deliveryOption) {
		try{
			if(deliveryOption==1 || deliveryOption==2 || deliveryOption==3) {
			sessionCart.setDeliveryOption(deliveryOption);
			System.out.println("Update Cart Deliery Option successful! " + sessionCart.getDeliveryOption());
			
			return true;
			}
			else {
				sessionCart.setCartTotal(sessionCart.getCartTotal().add(new BigDecimal(10)));
				System.out.println("Error: Update Cart Deliery Option unsuccessful. Posted value = " + sessionCart.getDeliveryOption());
				return false;
			}
		}catch(Exception e) {
		System.out.println("Exception encountered.");
		return false;
		}		
	}
	
}
