package ecommerce.interfacemethods;

import ecommerce.model.Product;
import ecommerce.model.ProductCategory;

import java.util.List;
import java.util.Optional;

// Author: Angeline

public interface AdminInterface {

    Optional<Product> findByProductId(long id);

    Product saveProduct(Product product);

    void deleteProduct(long id);

    List<ProductCategory> getProductCategories();

    boolean isLinkedToPendingOrder(long id);

}
