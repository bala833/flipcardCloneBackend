package com.bala.FlipkartClone.repository;

import com.bala.FlipkartClone.dto.ProductDTO;
import com.bala.FlipkartClone.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Query("SELECT p from ProductModel p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword,  '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword,  '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword,  '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword,  '%'))")
    public List<ProductModel> searchProducts(String keyword);
}
