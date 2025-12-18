package com.bala.FlipkartClone.controller;

import com.bala.FlipkartClone.dto.BannerDTO;
import com.bala.FlipkartClone.model.Banner;
import com.bala.FlipkartClone.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.bala.FlipkartClone.service.BannerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerService service;

    @GetMapping()
    public ResponseEntity<List<BannerDTO>> getAllBanners() {

        List<BannerDTO> bn = service.getAllBanners();
        return ResponseEntity.ok(bn);
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> addBanner( @RequestPart("banner") Banner bn,
                                                          @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException {
        Map<String, String> response = service.addBanners(bn, file);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateBanner(
            @PathVariable Long id,
            @RequestPart("banner") Banner data,
            @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException {
        Map<String, String> response = service.updateBanner(id, data, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerDTO> getBannerById(@PathVariable Long id) {
        BannerDTO response = service.getBannerById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, String>> deactivateBanner(@PathVariable Long id) {
        Map<String, String> response = service.deactivatebanner(id);
        return ResponseEntity.ok(response);
    }
}
