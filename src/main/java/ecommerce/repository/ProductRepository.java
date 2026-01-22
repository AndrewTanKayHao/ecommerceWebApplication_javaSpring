package ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import ecommerce.model.Product;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ProductRepository extends JpaRepository<Product, Long>{

	//Author: Angeline, Chee Wee

	@Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			"OR LOWER(p.category.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT p FROM Product p JOIN p.category c WHERE LOWER(c.category) LIKE LOWER(CONCAT('%', :category, '%'))")
	Page<Product> findByCategory(@Param("category") String category, Pageable pageable);

	Product findByName(String name);
	
	
//	Author: Dang Lam
	@Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stockQuantity = :quantity WHERE p.id = :productId")
    int updateQuantity(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
