package com.bala.FlipkartClone.service;

import com.bala.FlipkartClone.dto.BannerDTO;
import com.bala.FlipkartClone.model.Banner;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public interface BannerService {

    Map<String, String> addBanners(Banner data, MultipartFile image) throws IOException;

    List<BannerDTO> getAllBanners();

//    Map<String, String> updateBanner(Long id);

    BannerDTO getBannerById(Long id);

    Map<String, String> updateBanner(Long id, Banner data, MultipartFile file) throws IOException;

    Map<String, String> deactivatebanner(Long id);
}
