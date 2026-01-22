package ecommerce.controller;

import ecommerce.model.Customer;
import ecommerce.service.CustomerImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Author: Theingi Myint

@Controller
public class CustomerRegistrationController {

    @Autowired
    private CustomerImplementation customerService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if(!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "registration-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //Validate the input fields
        if(bindingResult.hasErrors()){
            return "registration-form";
        }
        try{
            customerService.registerNewCustomer(customer);
        }catch (IllegalArgumentException e){
            bindingResult.rejectValue("email","customer.email", "Email is already registered");
            return "registration-form";
        }

        redirectAttributes.addFlashAttribute("message","Registration Successful");
        return "redirect:/login";
    }
}


