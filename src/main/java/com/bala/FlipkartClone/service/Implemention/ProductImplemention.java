package com.bala.FlipkartClone.service.Implemention;

import com.bala.FlipkartClone.dto.ProductDTO;
import com.bala.FlipkartClone.model.ProductModel;
import com.bala.FlipkartClone.repository.ProductRepository;
import com.bala.FlipkartClone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class ProductImplemention implements ProductService {
//    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";
    private final String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath() + "/";



    @Autowired
    ProductRepository prod;

    @Autowired
    private ModelMapper mapper;

    public ProductDTO convertToDTO(ProductModel product) {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setCost_price(product.getCost_price()); // underscore fields
        dto.setDiscount(product.getDiscount());
        dto.setQuantity_in_stock(product.getQuantity_in_stock()); // underscore fields
        dto.setUnit(product.getUnit());
        dto.setStatus(product.getStatus());
        dto.setImage_url(product.getImage_url());
        dto.setCreated_by(product.getCreated_by());
        dto.setUpdated_by(product.getUpdated_by());

        return dto;
    }


    public ProductModel convertToEntity(ProductDTO dto) {
        ProductModel product = new ProductModel();
        BeanUtils.copyProperties(dto, product);
        return product;
    }
    // Get all products
    public Page<ProductDTO> getAllProduct(Pageable pageable) {
//        List<ProductModel> products = prod.findAll();
//        System.out.println(products);
//        return products.stream()
//                .filter(e -> e.getStatus().equals("ACTIVE"))
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());

        return prod.findAll(pageable)
                .map(product ->
                    { ProductDTO dto = mapper.map(product, ProductDTO.class);
                    if (product.getImage() != null) {
                        dto.setImage_url(
                                Base64.getEncoder().encodeToString(product.getImage())
                        );
                        dto.setImage_type(product.getImage_type());
                    }
                        return dto;
                    });
    }

    public ProductDTO GetProductById(Long id) {

             ProductModel product = prod.findById(id).orElseThrow( () -> new RuntimeException("Proudct notfound with id: " + id));

            ProductDTO dto = mapper.map(product, ProductDTO.class);
            if (product.getImage() != null) {
                dto.setImage_url(
                        Base64.getEncoder().encodeToString(product.getImage())
                );
                dto.setImage_type(product.getImage_type());
            }
            return dto;
    }

    public void AddProduct(ProductModel p, MultipartFile file) throws IOException {

//        if (file != null && !file.isEmpty()) {
//
////            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
////            replace the original image name with index
//            String fileName = UUID.randomUUID() + (file.getOriginalFilename().contains(".")
//                    ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
//                    : "");
////            TODO: instead of save image on local static/upload we can save the image on any cdn and same url we can save to setImage_url
//            Files.createDirectories(Paths.get(uploadDir));
//            file.transferTo(new File(uploadDir + fileName));
//            p.setImage_url("/uploads/" + fileName);
//
//        } else {
//            p.setImage_url(null); // or "/uploads/default.png"
//        }

        if (file != null && !file.isEmpty()) {
            p.setImage(file.getBytes());
            p.setImage_type(file.getContentType());
        } else {
            p.setImage(null);
            p.setImage_type(null);
        }


        prod.save(p);
    }

    public void UpdateProductById(Long id, ProductModel p, MultipartFile file) throws IOException {
        Optional<ProductModel> prod_value = prod.findById(id);

        if (prod_value.isPresent()) {
            ProductModel product = prod_value.get();
            System.out.println(product+ "ppp");

////            get existing image name for the product
//            String storedImagePath = product.getImage_url();
//            // Extract only the filename part
//            String storedFileName = null;
//            if (storedImagePath != null) {
//                storedFileName = storedImagePath.substring(storedImagePath.lastIndexOf('/') + 1);
//            }
//
//            String uploadFileName= null;
//
//            if (file != null && !file.isEmpty()) {
//                uploadFileName = file.getOriginalFilename();
//            }
//
//            System.out.println(uploadFileName + storedFileName + "file name");
//
////            compare
//            if (uploadFileName != null && uploadFileName.equalsIgnoreCase(storedFileName)){
//                p.setImage_url(product.getImage_url());
//                System.out.println("don't update");
//            } else if (file != null && !file.isEmpty()) {
//
//                //            replace the original image name with index
//                String fileName = UUID.randomUUID() + (file.getOriginalFilename().contains(".")
//                        ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
//                        : "");
////            TODO: instead of save image on local static/upload we can save the image on any cdn and same url we can save to setImage_url
//                Files.createDirectories(Paths.get(uploadDir));
//                file.transferTo(new File(uploadDir + fileName));
//                p.setImage_url("/uploads/" + fileName);
//            } else {
//                p.setImage_url(product.getImage_url());
//            }

            // 3️⃣ Handle optional image update
            if (file != null && !file.isEmpty()) {
                p.setImage(file.getBytes());
                p.setImage_type(file.getContentType());
            }

            p.setId(product.getId());
            prod.save(p);
        }
    }


    public void DeactivateProductById(Long id) {
        var prod_value = prod.findById(id);
        if (prod_value.isPresent()) {
            ProductModel prod_get = prod_value.get();
            prod_get.setStatus("DEACTIVATE");
            prod.save(prod_get);
        }

    }

    public List<ProductModel> serachProducts(String keyword) {
        return prod.searchProducts(keyword);
    }

}
