package com.bala.FlipkartClone.repository;

import com.bala.FlipkartClone.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Banners extends JpaRepository<Banner, Long> {
}
