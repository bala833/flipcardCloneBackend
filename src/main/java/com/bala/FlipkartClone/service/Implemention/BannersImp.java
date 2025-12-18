package com.bala.FlipkartClone.service.Implemention;

import com.bala.FlipkartClone.dto.BannerDTO;
import com.bala.FlipkartClone.model.Banner;
import com.bala.FlipkartClone.model.ProductModel;
import com.bala.FlipkartClone.repository.Banners;
import com.bala.FlipkartClone.service.BannerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BannersImp implements BannerService {


    @Autowired
    Banners bn;

    @Autowired
    private ModelMapper mapper;

    public Map<String, String> addBanners(Banner data, MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();


        if (file != null && !file.isEmpty()) {
            data.setImage(file.getBytes());
//            data.setImage_url(
//                    Base64.getEncoder().encodeToString(data.getImage())
//            );
            data.setImage_type(file.getContentType());
        } else {
            data.setImage(null);
            data.setImage_type(null);
        }
        bn.save(data);

        response.put("message", "Banner added successfully");
        response.put("status", "200");

        return response;
    }


    private BannerDTO convertTobannerDTO(Banner banner) {
        BannerDTO dto = new BannerDTO();
        dto.setId(banner.getId());
        dto.setName(banner.getName());
        dto.setDescription(banner.getDescription());
        dto.setImage_url(Base64.getEncoder().encodeToString(banner.getImage())); // do NOT send byte[]
        dto.setActive(banner.getActive()); // do NOT send byte[]
        return dto;
    }

    @Override
    public List<BannerDTO> getAllBanners() {
        List<Banner> data = bn.findAll();
        return data.stream()
//                .filter(Banner::isActive)
                .map(this::convertTobannerDTO)
                .toList();
    }

//    public Map<String, String> updateBanner(Long id) {
//        Banner data = bn.findById(id).orElseThrow( () -> new RuntimeException("banner notfound with id: " + id));
//
//        BannerDTO dto = mapper.map(data, BannerDTO.class);
//        if (data.getImage() != null) {
//            dto.setImage_url(
//                    Base64.getEncoder().encodeToString(data.getImage())
//            );
//            dto.setImage_type(data.getImage_type());
//        }
//
//    }

    public BannerDTO getBannerById(Long id) {
        Map<String, String> response = new HashMap<>();

        Banner data = bn.findById(id).orElseThrow( () -> new RuntimeException("banner notfound with id: " + id));

        BannerDTO dto = mapper.map(data, BannerDTO.class);
        if (data.getImage() != null) {
            dto.setImage_url(
                    Base64.getEncoder().encodeToString(data.getImage())
            );
            dto.setImage_type(data.getImage_type());
        }


        return dto;

    }

    public Map<String, String> updateBanner(Long id, Banner data, MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();

        Optional<Banner> banner_value = bn.findById(id);

        if (banner_value.isPresent()) {
            Banner bn_v = banner_value.get();
            System.out.println(bn_v + "ppp");

            if (file != null && !file.isEmpty()) {
                data.setImage(file.getBytes());
                data.setImage_type(file.getContentType());
            }
            data.setImage_type("null");
            data.setId(bn_v.getId());
            bn.save(data);
            response.put("message", "Banner added successfully");
            response.put("status", "200");

        }
        return response;
    }


    public Map<String, String> deactivatebanner(Long id) {
        Map<String, String> response = new HashMap<>();

        Banner data = bn.findById(id).orElseThrow(() -> new RuntimeException("Banner not found with :" + id));

        if (data != null) {
            data.setActive(false);
        }

        bn.save(data);
        response.put("message", "Banner deactivated successfully");
        response.put("status", "200");

        return response;

    }

}
