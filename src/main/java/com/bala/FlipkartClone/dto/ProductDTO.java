package com.bala.FlipkartClone.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    // Manual getters and setters
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String category;
    private String brand;
    private Double price;

    private Double cost_price;
    private Double discount;

    private Integer quantity_in_stock;
    private String unit;
    private String status;

    private String image_url;

    private String created_by;

    private String updated_by;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_type")
    private String  image_type;



    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setQuantity_in_stock(Integer quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public Integer getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public String getUnit() {
        return unit;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public byte[] getImage() {
       return image;
    }

    public String getImage_type() {
        return image_type;
    }
}
