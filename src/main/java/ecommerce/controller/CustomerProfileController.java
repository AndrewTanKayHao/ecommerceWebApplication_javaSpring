package ecommerce.controller;

import ecommerce.dto.CustomerProfileForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ecommerce.model.Customer;
import ecommerce.service.CustomerImplementation;

// Author: Theingi Myint, Honey And Nyunt

@Controller
public class CustomerProfileController {
    @Autowired
    private CustomerImplementation customerService;

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customer);
        return "profile-view";
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);
        // populate form DTO to avoid validating fields like email/password on edit
        CustomerProfileForm form = new CustomerProfileForm(customer.getFullName(), customer.getAddress(), customer.getPhoneNumber());
        model.addAttribute("customerForm", form);
        // keep the customer in the model so the template can display read-only fields like email
        model.addAttribute("customer", customer);
        return "profile-view";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("customerForm") CustomerProfileForm updatedForm, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            // return to the edit page with the form and errors
            model.addAttribute("customerForm", updatedForm);
            model.addAttribute("customer", customer);
            model.addAttribute("error", "Please fill all the fields correctly!. Current changes not saved!");
            return "profile-view";
        }
        try {
            customer.setFullName(updatedForm.getFullName());
            customer.setAddress(updatedForm.getAddress());
            customer.setPhoneNumber(updatedForm.getPhoneNumber());
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully");
            return "redirect:/profile";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Profile updated failed");
            return "redirect:/profile";
        }
    }

    @GetMapping("/profile/change-password")
    public String showChangePasswordForm() {
        return "profile-view";
    }

    @PostMapping("/profile/change-password")
    public String processChangePassword(org.springframework.web.context.request.WebRequest request, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!customerService.checkPassword(customer, currentPassword)) {
            model.addAttribute("errors", "Current password is incorrect.");
            model.addAttribute("customer", customer);
            model.addAttribute("showPasswordModal", true);
            return "profile-view";
        }
        if (newPassword == null || newPassword.length() < 8) {
            model.addAttribute("errors", "New password must be at least 8 characters.");
            model.addAttribute("customer", customer);
            model.addAttribute("showPasswordModal", true);
            return "profile-view";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errors", "New passwords do not match.");
            model.addAttribute("customer", customer);
            model.addAttribute("showPasswordModal", true);
            return "profile-view";
        }
        try {
            customerService.updatePassword(customer, newPassword);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
            return "redirect:/profile";
        }catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Password updated unsuccessful.");
        return "redirect:/profile";
        }
    }
}
