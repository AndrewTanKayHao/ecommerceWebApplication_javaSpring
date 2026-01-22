package ecommerce.controller;

import ecommerce.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.interfacemethods.CartInterface;
import ecommerce.interfacemethods.ProductInterface;
import jakarta.servlet.http.HttpSession;

// Author: Andrew
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ProductInterface productService;
	
	@Autowired
	private CartInterface cartService;
	
	@GetMapping("")
	public String postViewCart(HttpSession sessionObj, Model model, 
		@RequestParam(value= "page", defaultValue= "1")int page,  
		@RequestParam(value= "keyword", required = false) String keyword,
		@RequestParam(value= "category", required = false) String category) {
		/*INTEGRATION CODE*/
		/*On successful login, this will create or retrieve a Customer object based on whether there is a matching Spring Security user email. 
		 *The session Cart will also be instantiated.
		 *Refer to launchCustomerCart() method in CustomerImplementation for more details.*/
		if (sessionObj.getAttribute("sessionCart")== null) {
			System.out.println("---- Initialzing Customer and Cart instances. ----");
			return "forward:/initialize-customer-cart";
			}
		else {
			System.out.println("---- Initialized " + ((Cart) sessionObj.getAttribute("sessionCart")).getCustomer() + " ----" + "\n---- Initialized " 
					+ ((Cart) sessionObj.getAttribute("sessionCart")).toString() + " ----");
		}
		/*END OF INTEGRATION CODE*/
	
	//Cart has a method to calculate price and pass to checkout. after payment success, create and save order/orderdetails. 
		System.out.println("---- Cart page ----");
		model.addAttribute("sessionCart", sessionObj.getAttribute("sessionCart"));
		model.addAttribute("sessionCustomer", sessionObj.getAttribute("sessionCustomer"));
		
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword !=null? keyword:"");
		model.addAttribute("category", category !=null? category: "");
		System.out.println("Found Customer = " + ((Customer) sessionObj.getAttribute("sessionCustomer")).getFullName());
		System.out.println("Found Cart = " + ((Cart) sessionObj.getAttribute("sessionCart")).toString());
		return "cart";
		}

	// Author: Chee Wee
	@GetMapping("/back")
	public String backToBrowse(HttpSession sessionObj, Model model,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "category", required = false) String category) {

	    // Ensure cart and customer still exist in session
	    if (sessionObj.getAttribute("sessionCart") == null) {
	        System.out.println("---- Reinitializing cart ----");
	        return "forward:/initialize-customer-cart";
	    }

	    Cart cart = (Cart) sessionObj.getAttribute("sessionCart");

	    // Same logic as ProductController homepage
	    Pageable pageable = PageRequest.of(page - 1, 4);
	    Page<Product> productPage;

	    if (keyword != null && !keyword.isEmpty()) {
	        productPage = productService.searchByKeyword(keyword, pageable);
	    } else if (category != null && !category.isBlank()) {
	        productPage = productService.findByCategory(category, pageable);
	    } else {
	        productPage = productService.getAllProducts(pageable);
	    }

	    model.addAttribute("products", productPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("keyword", keyword != null ? keyword : "");
	    model.addAttribute("category", category != null ? category : "");
	    model.addAttribute("sessionCart", cart);

	    System.out.println("---- Returning to homepage with existing cart ----");
	    return "homepage";
	}

	@GetMapping("/clear")
	public String clearCart(HttpSession sessionObj, Model model) {
		if (sessionObj.getAttribute("sessionCart") != null) {
			System.out.println("Clearing cart.");
			cartService.clearCart((Cart) sessionObj.getAttribute("sessionCart"));
		}
		else {
			System.out.println("Cannor clear cart. No cart in sessionObj.");
		}
		return "forward:/cart";
		}


}
