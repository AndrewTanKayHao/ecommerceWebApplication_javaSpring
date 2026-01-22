package ecommerce.controller;

import ecommerce.model.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.interfacemethods.CartInterface;
import ecommerce.interfacemethods.CustomerInterface;
import ecommerce.interfacemethods.OrderDetailInterface;
import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.ProductInterface;
import org.springframework.web.bind.annotation.RequestParam;

// Author: Chee Wee and Angeline

@Controller
public class HomepageController {

	private final ProductInterface productService;

	public HomepageController(ProductInterface productService) {
		this.productService = productService;
	}
	
	//update setting to set homepage at page "1" with a size of 8 items per page
		@GetMapping("/")
		public String homepage(HttpSession sessionObj, Model model,
				@RequestParam(name = "page", defaultValue = "1") int page,
				@RequestParam(name = "size", defaultValue = "4") int size,
				@RequestParam(name = "keyword", required = false)String keyword,
				@RequestParam(name = "category", required = false)String category)
		{
			if (sessionObj.getAttribute("sessionCart")== null) {
				System.out.println("---- Homepage Cart Not found. ----");
				}
			else {
				System.out.println("---- Homepage Cart Found. " + ((Cart) sessionObj.getAttribute("sessionCart")).getCustomer().getId() + 
						" ----" + "\n---- Initialized " + ((Cart) sessionObj.getAttribute("sessionCart")).toString() + " ----");
				}
			
			Pageable pageable = PageRequest.of(page -1, size);
			Page<Product> productPage;
			
			//Search by keyword
			if (keyword != null && !keyword.isEmpty()) {
			productPage	=  productService.searchByKeyword(keyword, pageable);
			model.addAttribute("keyword", keyword);
			model.addAttribute("category", "");
			}
			//if no keyword, filter by category
			else if (category != null && !category.isBlank()) {
			productPage = productService.findByCategory(category, pageable);
			model.addAttribute("category", category);
			model.addAttribute("keyword", "");
			}
			else {
			productPage = productService.getAllProducts(pageable);
			model.addAttribute("category", "");
			model.addAttribute("keyword", "");
			}
			model.addAttribute("products", productPage.getContent());
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", productPage.getTotalPages());
			
			Cart cart = (Cart)sessionObj.getAttribute("sessionCart");
			model.addAttribute("sessionCart", cart);
			
			return "homepage";
		}


	@GetMapping("/search")
	public String findProduct(@RequestParam("keyword") String keyword, 
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "4") int size,
			HttpSession sessionObj, Model model) {

		Page<Product> productPage;

		if (keyword == null || keyword.isBlank()) {
			Pageable pageable = PageRequest.of(page -1, size);
			productPage = productService.getAllProducts(pageable);
		} else {
			Pageable pageable = PageRequest.of(page -1, size);
			productPage = productService.searchByKeyword(keyword, pageable);
		}

		model.addAttribute("products", productPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("keyword", keyword);
		
		Cart cart = (Cart)sessionObj.getAttribute("sessionCart");
		model.addAttribute("sessionCart", cart);
		System.out.println("<-----Searching for product--("+ keyword+ ") ----->");
		
		return "homepage";
	}

	@GetMapping("/category")
	public String filterByCategory(@RequestParam("category") String category, 
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "4") int size,
			HttpSession sessionObj, Model model) {

		Page<Product> productPage;

		if(category == null || category.isBlank()){
			Pageable pageable = PageRequest.of(page -1, size);
			productPage = productService.getAllProducts(pageable);
		} else {
			Pageable pageable = PageRequest.of(page -1, size);
			productPage = productService.findByCategory(category, pageable);
		}
		model.addAttribute("products", productPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("category", category);
		
		Cart cart = (Cart)sessionObj.getAttribute("sessionCart");
		model.addAttribute("sessionCart", cart);
		System.out.println("<-----Sort by for category--("+ category+ ") ----->");
		return "homepage";
	}
}
