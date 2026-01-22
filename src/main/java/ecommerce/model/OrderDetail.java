package ecommerce.model;

import jakarta.persistence.*;

//Authors: Andrew, Dang Lam

@Entity
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Product product;

	@ManyToOne
	private Order order;

	private int orderedQuantity;

	public OrderDetail() {}

	public OrderDetail(Product product, int quantity) {
		this.product = product;
		this.orderedQuantity = quantity;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Order getOrder() {
		return order;
	}


}
