package ecommerce.service;

import ecommerce.interfacemethods.CustomerInterface;
import ecommerce.model.Cart;
import ecommerce.model.Customer;
import ecommerce.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Author: Theingi Myint

@Service
public class CustomerImplementation implements CustomerInterface {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CartImplementation cartService;

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public boolean checkPassword(Customer customer, String rawPassword) {
        return passwordEncoder.matches(rawPassword, customer.getPassword());
    }

    @Override
    @Transactional
    public void updatePassword(Customer customer, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
        customerRepository.save(customer);
        // Optionally, update password in Spring Security UserDetailsManager
        if (userDetailsManager.userExists(customer.getEmail())) {
            org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User
                    .withUsername(customer.getEmail())
                    .password(encodedPassword)
                    .roles("CUSTOMER");
            userDetailsManager.updateUser(builder.build());
        }
    }

    @Override
    @Transactional // This ensures both database operations succeed or fail together.
    public void registerNewCustomer(Customer customer) {

        String username = customer.getEmail(); //use email as username
        if(userDetailsManager.userExists(username)) {
            throw new IllegalArgumentException("Email already exists"  + username);
        }

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.setEnabled(true);
        customerRepository.save(customer);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(encodedPassword)
                .roles("CUSTOMER")
                .build();

        userDetailsManager.createUser(userDetails);
    }

    @Override
    public List<Customer> getAllCustomers () {
        return customerRepository.findAll();
    }

    //Author: Andrew

    /*INTEGRATION CODE*/

    @Override
    @Transactional // Links a successful user login to the respective customer and cart objects.
    public Cart launchCustomerCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("No authenticated user found.");
            return null;
        }

        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        Customer sessionCustomer = customerRepository.findByEmail(username);
        if (sessionCustomer == null) {
            System.out.println("No customer found.");
            return null;
        }

        Cart sessionCart = cartService.createCart(sessionCustomer);

        System.out.println("New cart initialized for " + username);
        return sessionCart;
    }
        /*INTEGRATION CODE*/

        public void save (Customer customer){
            customerRepository.save(customer);
            System.out.println("Customer saved!");
        }

}
