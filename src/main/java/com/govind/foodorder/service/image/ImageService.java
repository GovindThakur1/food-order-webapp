package com.govind.foodorder.service.image;

import com.govind.foodorder.exception.ResourceNotFoundException;
import com.govind.foodorder.model.FoodItem;
import com.govind.foodorder.model.Image;
import com.govind.foodorder.repository.ImageRepository;
import com.govind.foodorder.service.food.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final FoodItemService foodItemService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public List<Image> saveImage(List<MultipartFile> files, Long foodItemId) {
        FoodItem foodItem = foodItemService.getFoodItemById(foodItemId);
        List<Image> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setFoodItem(foodItem);

                // saving image for the first so that we get the id
                Image savedImage = imageRepository.save(image);

                // building download url using the id of saved image
                String buildDownloadUrl = "api/images/image/download/";
                String downloadUrl = buildDownloadUrl + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);

                imageRepository.save(savedImage);

                // use dto here
                savedImages.add(savedImage);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return savedImages;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotFoundException("Image not found");
                });

    }
}
