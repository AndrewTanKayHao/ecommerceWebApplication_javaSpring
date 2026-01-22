package ecommerce.model;

//Author: Andrew
public class CartItem {
	private Product product;
    private int orderedQuantity;

    public CartItem(Product product, int orderedQuantity) {
        this.product = product;
        this.orderedQuantity = orderedQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    @Override
    public String toString() {
        return "CartItem [product=" + product.getName() + ", Quantity=" + orderedQuantity + "]";
    }	
}
