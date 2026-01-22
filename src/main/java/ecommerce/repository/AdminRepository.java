package ecommerce.repository;

import ecommerce.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

// Author: Angeline

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
