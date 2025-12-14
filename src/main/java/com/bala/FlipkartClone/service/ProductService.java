package com.bala.FlipkartClone.service;

import com.bala.FlipkartClone.dto.ProductDTO;
import com.bala.FlipkartClone.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public interface ProductService {

    public Page<ProductDTO> getAllProduct(Pageable pageable);

    public void UpdateProductById(Long id, ProductModel prod, MultipartFile file) throws IOException;

    public void DeactivateProductById(Long id);

    public void AddProduct(ProductModel prod, MultipartFile file) throws IOException;

    public ProductDTO GetProductById(Long id);

    List<ProductModel> serachProducts(String keyword);
}
