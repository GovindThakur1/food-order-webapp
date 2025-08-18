package com.govind.foodorder.repository;

import com.govind.foodorder.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
