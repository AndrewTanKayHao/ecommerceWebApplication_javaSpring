package ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


import ecommerce.interfacemethods.CartInterface;
import ecommerce.interfacemethods.CheckoutInterface;
import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.PaymentInterface;
import ecommerce.model.Cart;
import ecommerce.model.Customer;
import ecommerce.model.Order;
import jakarta.servlet.http.HttpSession;

// Author: Dang Lam
@Controller
public class CheckoutController {
	
	@Autowired
	CheckoutInterface checkoutService;
	
	@Autowired
	CartInterface cartService;
	
	@Autowired
	OrderInterface orderService;
	
	@Autowired
	private Map<String, PaymentInterface> paymentServices;

    
	@GetMapping("/checkout")
	public String displayCheckoutPage(HttpSession session, Model model) {
		Customer customer = (Customer) session.getAttribute("sessionCustomer");
		if(customer == null) return "redirect:login";
		
		Cart cart = (Cart) session.getAttribute("sessionCart");
		if(cart.getCartItemList().size() == 0) return "redirect:/";
		
		if(!checkoutService.checkEnoughProduct(cart)) {
			model.addAttribute("message", "Product out of stock");
			return "cancel";
		}
		
		model.addAttribute("cart", cart);
		model.addAttribute("customer", customer);

		return "checkout";
	}
	
	
	@PostMapping("/checkout-cod")
	public RedirectView checkoutCod(HttpSession session) throws Exception		
	{
		Cart cart = (Cart) session.getAttribute("sessionCart");
		Order newOrder = checkoutService.reserveOrder(cart);
		
		PaymentInterface codService = paymentServices.get("codPaymentService");
		String view = codService.processPayment(newOrder);
		
		return new RedirectView(view);
	}
	
    @PostMapping("/checkout-stripe")
    public RedirectView createCheckoutSession(HttpSession session) throws Exception {
    	
    	Cart cart = (Cart) session.getAttribute("sessionCart");
		Order newOrder = checkoutService.reserveOrder(cart);

		PaymentInterface stripeService = paymentServices.get("stripePaymentService");
		String view = stripeService.processPayment(newOrder);
		
        return new RedirectView(view);
    }
    
    @GetMapping("/success")
    public String checkoutSuccess(@RequestParam("orderId") Long orderId, HttpSession session, Model model) {
    	Customer customer = (Customer) session.getAttribute("sessionCustomer");
        if(customer == null) {
            return "redirect:login";
        }
        Cart cart = (Cart) session.getAttribute("sessionCart");
		cartService.clearCart(cart);
		
		Order order = orderService.getOrderById(orderId);
		model.addAttribute("order", order);
		model.addAttribute("customer", customer);
    	return "purchase-success";
    }
    
    @GetMapping("/cancel")
    public String checkoutCancel(@RequestParam("orderId") Long orderId, Model model) {
    	Order order = orderService.getOrderById(orderId);
    	orderService.rollbackFailOrder(order);
    	return "cancel";
    }
}
