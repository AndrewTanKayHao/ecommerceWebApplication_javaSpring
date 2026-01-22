package ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

//Author: Angeline

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=30)
    @NotBlank(message = "Product category should not be blank")
    private String category;

    public ProductCategory() {}

    public ProductCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}