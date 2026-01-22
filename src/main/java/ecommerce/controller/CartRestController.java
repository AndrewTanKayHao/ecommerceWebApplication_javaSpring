package ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.model.Cart;
import ecommerce.model.Customer;
import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;
import ecommerce.service.CartImplementation;
import ecommerce.service.CustomerImplementation;
import ecommerce.service.ProductImplementation;
import jakarta.servlet.http.HttpSession;

// Author: Andrew
@RestController
@RequestMapping("/cart")
public class CartRestController {
	
	@Autowired
	CartImplementation cartService;
	
	@Autowired
	ProductImplementation productService;
	
	@PostMapping("/add")
	public Map<String, Object> addToCart(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
		
		System.out.println("---- addToCart RestController ----");

        Long productId = Long.valueOf(jsonPayload.get("id").toString());

        Product product = productService.findById(productId);
        if (product == null) {
            return Map.of(
                "success", false,
                "error", "Product not found"
            );
        }

        Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
        if (sessionCart == null) {
        	 return Map.of(
        	     "success", false,
        	     "error", "Cart not initialized."
        	);
        }

        boolean added = cartService.addCartProduct(sessionCart, product, 1);

        return Map.of(
            "success", added,
            "cartItemCount", cartService.getCartItemQuantity(sessionCart, productId)
        );
    }
	
	@PostMapping("/increase")
	public Map<String, Object> increaseCart(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
		
		System.out.println("---- addToCart RestController ----");

        Long productId = Long.valueOf(jsonPayload.get("id").toString());

        Product product = productService.findById(productId);
        if (product == null) {
            return Map.of(
                "success", false,
                "error", "Product not found"
            );
        }

        Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
        if (sessionCart == null) {
        	 return Map.of(
        	     "success", false,
        	     "error", "Cart not initialized."
        	);
        }

        boolean added = cartService.increaseCartProduct(sessionCart, product, 1);

        return Map.of(
            "success", added,
            "cartItemCount", cartService.getCartItemQuantity(sessionCart, productId)
        );
    }
	
	@PostMapping("/decrease")
	public Map<String, Object> decreaseCart(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
		
		System.out.println("---- addToCart RestController ----");

        Long productId = Long.valueOf(jsonPayload.get("id").toString());

        Product product = productService.findById(productId);
        if (product == null) {
            return Map.of(
                "success", false,
                "error", "Product not found"
            );
        }

        Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
        if (sessionCart == null) {
        	 return Map.of(
        	     "success", false,
        	     "error", "Cart not initialized."
        	);
        }

        boolean added = cartService.decreaseCartProduct(sessionCart, product, 1);

        return Map.of(
            "success", added,
            "cartItemCount", cartService.getCartItemQuantity(sessionCart, productId)
        );
    }
	
	@PostMapping("/remove")
	public Map<String, Object> removeFromCart(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
		
		System.out.println("---- removeFromCart RestController ----");

        Long productId = Long.valueOf(jsonPayload.get("id").toString());

        Product product = productService.findById(productId);
        if (product == null) {
            return Map.of(
                "success", false,
                "error", "Product not found"
            );
        }

        Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
        if (sessionCart == null) {
        	 return Map.of(
        	     "success", false,
        	     "error", "Cart not initialized."
        	);
        }

        boolean removed = cartService.removeCartProduct(sessionCart, product);

        return Map.of(
            "success", removed
        );
	}
	
	@PostMapping("/update-quantity")
	public Map<String, Object> updateCartQuantity(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
	    // Check if the 'id' and 'quantity' are present in the payload
	    if (jsonPayload.get("id") == null || jsonPayload.get("quantity") == null) {
	        return Map.of(
	            "success", false,
	            "error", "Product ID or quantity is missing"
	        );
	    }

	    // Extract the productId and quantity
	    Long productId = Long.valueOf(jsonPayload.get("id").toString());
	    Integer quantity = Integer.valueOf(jsonPayload.get("quantity").toString());

	    // Find the product by ID
	    Product product = productService.findById(productId);
	    if (product == null) {
	        return Map.of(
	            "success", false,
	            "error", "Product not found"
	        );
	    }

	    // Get the cart from the session
	    Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
	    if (sessionCart == null) {
	        return Map.of(
	            "success", false,
	            "error", "Cart not initialized."
	        );
	    }

	    // Call the service layer to update the quantity of the cart item
	    boolean success = cartService.updateCartQuantity(sessionCart, product, quantity);

	    // Return the success result
	    return Map.of(
	        "success", success,
	        "cartItemCount", cartService.getCartItemQuantity(sessionCart, productId)
	    );
	}
	
	@PostMapping("/update-delivery")
	public Map<String, Object> updateCartDeliveryOption(HttpSession sessionObj, @RequestBody Map<String, Object> jsonPayload) {
	    // Check if the 'deliveryOption' is present in the payload
	    if (jsonPayload.get("deliveryOption") == null) {
	        return Map.of(
	            "success", false,
	            "error", "Delivery option detail is missing"
	        );
	    }

	    // Extract the delivery option integer
	    Integer deliveryOption = Integer.valueOf(jsonPayload.get("deliveryOption").toString());

	    // Get the cart from the session
	    Cart sessionCart = (Cart) sessionObj.getAttribute("sessionCart");
	    if (sessionCart == null) {
	        return Map.of(
	            "success", false,
	            "error", "Cart not initialized."
	        );
	    }

	    // Call the service layer to update the quantity of the cart item
	    boolean success = cartService.updateCartDeliveryOption(sessionCart, deliveryOption);

	    // Return the success result
	    return Map.of(
	        "success", success,
	        "cartItemCount", cartService.getCartItemQuantity(sessionCart, deliveryOption)
	    );
	}
	
}
