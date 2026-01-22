package ecommerce.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

//Author: Andrew and Chee Wee

public class Cart {
	
	//uses customer id
	private long id;
	private ArrayList<CartItem> cartItemList;

	@OneToOne
	@MapsId //use customerID as cartID
	@JoinColumn(name="customer_id")
	private Customer customer;
	private BigDecimal cartTotal=BigDecimal.ZERO;
	private int deliveryOption = 0;
	
	
	public Cart() {
	}
	
	public Cart(Customer customer) {
		this.customer = customer;
		this.id = customer.getId();
		this.cartItemList = new ArrayList<CartItem>();
	}
	
	@Override
	public String toString() {
		return "Cart [Id: " + getId() + ", " + getCustomer() + ", Cart Items: " + cartItemList + ", Cart Total: $" + getCartTotal() + "]";
	}

	public BigDecimal getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(BigDecimal cartTotal) {
		this.cartTotal = cartTotal;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ArrayList<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(ArrayList<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(int deliveryOption) {
		this.deliveryOption = deliveryOption;}
	
	//Added to aid cart Qty keeping to pass back to homepage.
	public int getQtyForProduct(Long productId) {
		for (CartItem item : cartItemList) {
			if(item.getProduct().getId() == productId) {
				return item.getOrderedQuantity();
			}
		}
		return 0;
	}
	
	public boolean hasProduct(Long productId) {
		if (cartItemList == null || cartItemList.isEmpty()) {
			return false;
		}
		return cartItemList.stream()
				.anyMatch(item -> item.getProduct().getId() == productId);
	}
}