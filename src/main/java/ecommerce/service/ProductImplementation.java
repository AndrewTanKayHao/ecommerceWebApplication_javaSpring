package ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ecommerce.interfacemethods.ProductInterface;
import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductImplementation implements ProductInterface{

	//Author: Chee Wee and Angeline

	@Autowired
	ProductRepository productRepo;
	
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	@Override
	public Page<Product> getAllProducts(Pageable pageable){
			return productRepo.findAll(pageable);
		}

	@Override
	public Page<Product> searchByKeyword(String keyword, Pageable pageable){
		return productRepo.searchByKeyword(keyword, pageable);
	}

	@Override
	public Page<Product> findByCategory(String category, Pageable pageable){
		return productRepo.findByCategory(category, pageable);
	}

	//Author: Andrew

	@Override
	public void save(Product product) {
		productRepo.save(product);
	}
	//update for Pagination. (KIV change the above to use page instead of list to streamline)

	@Override
	public Product findByName(String name) {
		return productRepo.findByName(name);
	}
	
	@Override
	public Product findById(long id) {
		return (productRepo.findById(id).get());
	}
	
//	Author: Dang Lam
	@Override
	public void updateProductQuantity(long id, int quantity) {
		productRepo.updateQuantity(id, quantity);
	}
}