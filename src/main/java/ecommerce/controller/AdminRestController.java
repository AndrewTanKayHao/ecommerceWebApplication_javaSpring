package ecommerce.controller;

import ecommerce.interfacemethods.AdminInterface;
import ecommerce.interfacemethods.ProductInterface;
import ecommerce.model.Admin;
import ecommerce.model.Product;
import ecommerce.model.ProductCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Author: Angeline
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class AdminRestController {

    @Autowired
    private AdminInterface adminService;

    @Autowired
    private ProductInterface productService;

    @Autowired
    private AuthenticationManager authManager;

    //For postman login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword())
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/category")
    public List<ProductCategory> listAllCategories() {
        return adminService.getProductCategories();
    }

    @GetMapping("/product")
    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable("id") long id) {

        Optional<Product> optProduct = adminService.findByProductId(id);

        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            return new ResponseEntity<>(product, HttpStatus.OK); //200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404 NOT FOUND
        }
    }

    @PostMapping("/product/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            result.getFieldErrors().forEach(e ->
                    errorMap.put(e.getField(), e.getDefaultMessage())
            );

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST); // 400 BAD REQUEST (validation failed)
        }

        Product newProduct = adminService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED); // 201 CREATED

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @Valid @RequestBody Product inProduct, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            result.getFieldErrors().forEach(e ->
                    errorMap.put(e.getField(), e.getDefaultMessage())
            );

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST); // 400 BAD REQUEST (validation failed)
        }

        Optional<Product> optProduct = adminService.findByProductId(id);

        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            product.setName(inProduct.getName());
            product.setCategory(inProduct.getCategory());
            product.setDescription(inProduct.getDescription());
            product.setStockQuantity(inProduct.getStockQuantity());
            product.setImageUrl(inProduct.getImageUrl());
            product.setUnitPrice(inProduct.getUnitPrice());

            Product updatedProduct = adminService.saveProduct(product);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK); //200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404 NOT FOUND
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        if (adminService.isLinkedToPendingOrder(id)) {
            return new ResponseEntity<>("Cannot delete product. Product is linked to pending order(s).", HttpStatus.CONFLICT);
            // 409 CONFLICT
        }

        try {
            adminService.deleteProduct(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT); // 204 NO CONTENT
        }catch (Exception e){
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
        }
    }


}
