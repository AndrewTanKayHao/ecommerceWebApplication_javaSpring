package ecommerce.repository;

import ecommerce.model.Product;
import ecommerce.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Author: Angeline

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
