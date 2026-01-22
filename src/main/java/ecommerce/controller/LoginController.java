package ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.model.Cart;
import ecommerce.service.CustomerImplementation;
import jakarta.servlet.http.HttpSession;

// Author: Theingi Myint
@Controller
public class LoginController {

	@Autowired
	CustomerImplementation customerService;
	
    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/accessDeniedPage")
    public String showAccessDeniedPage(){
        return "access-denied";
    }

    // Author: Andrew
    /*INTEGRATION CODE*/
    @GetMapping("/initialize-customer-cart")
    public String initializeCustomerCart(HttpSession sessionObj) {
		/*On successful login, this will create or retrieve a Customer object based on whether there is a matching Spring Security user email. 
		 *The session Cart will also be instantiated.
		 *Refer to launchCustomerCart() method in CustomerImplementation for more details.*/
		Cart sessionCart = customerService.launchCustomerCart();
		sessionObj.setAttribute("sessionCart", sessionCart);
		sessionObj.setAttribute("sessionCustomer", sessionCart.getCustomer());
		System.out.println("---- Initialzed " + sessionCart.getCustomer() + " " + sessionCart + " ----");
    	return "redirect:/";
    }
    /*END OF INTEGRATION CODE*/
}

