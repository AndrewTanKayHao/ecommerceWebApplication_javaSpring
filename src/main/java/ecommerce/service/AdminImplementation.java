package ecommerce.service;

import ecommerce.interfacemethods.AdminInterface;
import ecommerce.model.Product;
import ecommerce.model.ProductCategory;
import ecommerce.repository.OrderRepository;
import ecommerce.repository.ProductCategoryRepository;
import ecommerce.repository.ProductRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Author: Angeline

@Service
@Transactional
public class AdminImplementation implements AdminInterface {

    @Resource
    private ProductRepository productRepo;

    @Autowired
    private ProductCategoryRepository productCategoryRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public Optional<Product> findByProductId(long id) {
        return productRepo.findById(id);
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(long id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<ProductCategory> getProductCategories() {
        return productCategoryRepo.findAll();
    }

    @Override
    public boolean isLinkedToPendingOrder(long id) {
        return orderRepo.countPendingOrdersByProductId(id) > 0;
    }
}
