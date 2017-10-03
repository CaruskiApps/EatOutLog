package com.caruski.eatoutlog.repository;

import com.caruski.eatoutlog.domain.Image;

import java.util.List;

public interface ImageRepository {

    long createImage(Image image);

    Image getImage(long imageId);

    List<Image> getAllImages(long dishId);
}
