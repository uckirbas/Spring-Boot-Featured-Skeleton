package com.example.servicito.domains.promotions.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.promotions.models.entities.Promo;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.ImageInvalidException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import java.io.IOException;
import java.util.List;

public interface PromoService {
    Page<Promo> findAll(int page);
    Promo save(Promo promo) throws InvalidException;
    Promo update(Long promoId, Promo promo) throws InvalidException, NotFoundException;
    Promo findOne(Long id) throws NotFoundException;
    List<Promo> getLatestPromotions();

    void notifyUser(Long promoId) throws NotFoundException, ForbiddenException, UnknownException, InvalidException, JsonProcessingException;

    void uploadPhoto(Long promoId, MultipartFile multipartFile) throws ImageInvalidException, LimitExceededException, NotFoundException, IOException;
}
