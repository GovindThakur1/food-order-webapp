package com.govind.foodorder.service.image;

import com.govind.foodorder.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    List<Image> saveImage(List<MultipartFile> files, Long foodItemId);

    void updateImage(MultipartFile file, Long imageId);

    void deleteImageById(Long id);

}
