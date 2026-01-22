package ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

//Authors: Angeline, Chee Wee, Andrew, Dang Lam

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=50)
	@NotBlank(message = "Product name should not be blank")
	private String name;

	@NotBlank(message = "Product description should not be blank")
	private String description;

	@Digits(integer=6, fraction=2,  message = "Unit price must be 2 decimal place")
	@DecimalMin(value = "0.00", inclusive = false, message = "Unit price must be greater than 0")
	@NotNull(message = "Unit Price should not be blank")
	private BigDecimal unitPrice;

	@NotBlank(message = "Product imageURL should not be blank")
	private String imageUrl;

	@Min(value = 0, message = "Product quantity cannot be negative")
	@NotNull(message = "Stock quantity should not be blank")
	private int stockQuantity;
	
	@ManyToOne
	@NotNull(message = "Product category must be selected")
	private ProductCategory category;

	@JsonIgnore
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;

	public Product(){}

	public Product(String name, ProductCategory category, String description, BigDecimal unitPrice, String imageUrl, int quantity) {
		this.name=name;
		this.category=category;
		this.description=description;
		this.unitPrice=unitPrice;
		this.imageUrl=imageUrl;
		this.stockQuantity=quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int quantity) {
		this.stockQuantity = quantity;
	}

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", unitPrice=" + unitPrice +
				", imageUrl='" + imageUrl + '\'' +
				", stockQuantity=" + stockQuantity +
				", category=" + category +
				'}';
	}
}
