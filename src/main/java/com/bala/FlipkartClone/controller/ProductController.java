package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.dto.ProductDTO;
import com.bala.FlipkartClone.model.ProductModel;
import com.bala.FlipkartClone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> ProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size , Sort.by(sortBy));
        Page<ProductDTO> product = service.getAllProduct(pageable);
        return  ResponseEntity.ok(product);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> GetProductById(@PathVariable Long id) {
        ProductDTO product = service.GetProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/product", consumes = {"multipart/form-data"})
    public void AddProduct(
        @RequestPart("product") ProductModel prod,
        @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException {
        service.AddProduct(prod, file);
    }

    @PutMapping("/product/{id}")
    public void UpdateProductById( @PathVariable Long id,
                                   @RequestPart("product") ProductModel prod,
                                   @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException  {
        service.UpdateProductById(id, prod, file);
    }

    @DeleteMapping("/product/{id}")
    public void DeactivateProductById(@PathVariable Long id) {
        service.DeactivateProductById(id);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductModel>> searchProducts(@RequestParam String keyword) {
        System.out.println(keyword + "aaaaa");
        List<ProductModel> products = service.serachProducts(keyword);
        return  new ResponseEntity<>(products, HttpStatus.OK);
    }
}
