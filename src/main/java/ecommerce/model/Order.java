package ecommerce.model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;

//Author: Andrew, Dang Lam

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private ZonedDateTime dateTime;

	private BigDecimal orderTotal;

	private String paymentMethod;

	@ManyToOne
    @JoinColumn(name="status_id")
	private OrderStatus status;
	
	@OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> orderDetailList= new ArrayList<OrderDetail>();

	@ManyToOne
	private Customer customer;
	
	public Order() {
		this.dateTime = ZonedDateTime.now(ZoneId.of("Asia/Singapore"));
	}
	
	public Order(Cart cart, OrderStatus Pending) {
		this.dateTime=ZonedDateTime.now(ZoneId.of("Asia/Singapore"));
		this.customer=cart.getCustomer();

		for (CartItem cartItem : cart.getCartItemList()){
			OrderDetail orderDetail = new OrderDetail(cartItem.getProduct(),cartItem.getOrderedQuantity());
			orderDetail.setOrder(this);
			this.orderDetailList.add(orderDetail);
		}

		this.setOrderTotal(cart.getCartTotal());
		this.status=Pending;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(ZonedDateTime date) {
		this.dateTime = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public OrderStatus getOrderStatus() {
		return status;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", dateTime=" + dateTime +
				", orderTotal=" + orderTotal +
				", paymentMethod='" + paymentMethod + '\'' +
				", status=" + status +
				", orderDetailList=" + orderDetailList +
				", customer=" + customer +
				'}';
	}
}
