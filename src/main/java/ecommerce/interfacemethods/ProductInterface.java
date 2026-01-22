package ecommerce.interfacemethods;

import java.util.List; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ecommerce.model.Product;

public interface ProductInterface {

	//Author: Angeline and Chee Wee
	List<Product> getAllProducts();
	Page<Product> getAllProducts(Pageable pageable);
	Page<Product> searchByKeyword(String keyword, Pageable pageable);
	Page<Product> findByCategory(String category, Pageable pageable);

	//Author: Andrew
	void save(Product product);
	Product findByName(String name);

	//Author: Dang Lam
	Product findById(long id);
	void updateProductQuantity(long id, int quantity);
}
