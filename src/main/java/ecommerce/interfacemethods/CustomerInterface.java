package ecommerce.interfacemethods;

import ecommerce.model.Cart;
import ecommerce.model.Customer;

import java.util.List;

public interface CustomerInterface {
	//Author: Andrew
	void save(Customer customer);
	Cart launchCustomerCart();

	//Author: Theingi Myint
	void registerNewCustomer(Customer customer);
	List<Customer> getAllCustomers();
	Customer getCustomerByEmail(String email);
	boolean checkPassword(Customer customer, String rawPassword);
	void updatePassword(Customer customer, String newPassword);
}
