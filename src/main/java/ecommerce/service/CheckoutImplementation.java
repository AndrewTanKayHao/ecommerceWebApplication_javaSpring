package ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ecommerce.interfacemethods.CheckoutInterface;
import ecommerce.interfacemethods.OrderInterface;
import ecommerce.model.Cart;
import ecommerce.model.CartItem;
import ecommerce.model.Order;
import jakarta.transaction.Transactional;

//Author: Dang Lam

@Service
@Transactional
public class CheckoutImplementation implements CheckoutInterface{
	
	@Autowired
	OrderInterface orderService;
	
	@Override
	@Transactional
	public Order reserveOrder(Cart cart){
		Order newOrder = orderService.converCartToOrder(cart);
		try {
            orderService.saveOrderAndProductQuantity(newOrder);
        } catch (IllegalStateException e) {
        	throw new RuntimeException("products out of stock");
        }
        return newOrder;
	}
		
	@Override
	public boolean checkEnoughProduct(Cart cart) {
		for(CartItem item: cart.getCartItemList()) {
			if(item.getProduct().getStockQuantity() < item.getOrderedQuantity()) {
				return false;
			}
		}
		return true;
	}
}
