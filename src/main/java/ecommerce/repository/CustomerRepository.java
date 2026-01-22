package ecommerce.repository;

import ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//Author: Andrew

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByEmail(String email);
}
