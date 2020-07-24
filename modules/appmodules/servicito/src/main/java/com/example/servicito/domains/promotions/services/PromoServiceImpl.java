package com.example.servicito.domains.promotions.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.coreweb.utils.PageAttr;
import com.example.common.utils.Validator;
import com.example.servicito.domains.notifications.models.pojo.NotificationData;
import com.example.servicito.domains.notifications.models.pojo.PushNotification;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.servicito.domains.promotions.models.entities.Promo;
import com.example.servicito.domains.promotions.repositories.PromoRepository;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.ImageInvalidException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import java.io.IOException;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    private final PromoRepository promoRepo;
    private final NotificationService notificationService;
    private final FileUploadService fileUploadService;

    @Autowired
    public PromoServiceImpl(PromoRepository promoRepo, NotificationService notificationService, FileUploadService fileUploadService) {
        this.promoRepo = promoRepo;
        this.notificationService = notificationService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Page<Promo> findAll(int page) {
        return this.promoRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Promo save(Promo promo) throws InvalidException {
        if (promo == null) throw new IllegalArgumentException("Promo can not be empty!");
        if (Validator.nullOrEmpty(promo.getTitle()))
            throw new InvalidException("Title can not be null or empty!");
        return this.promoRepo.save(promo);
    }

    @Override
    public Promo update(Long promoId, Promo promo) throws InvalidException, NotFoundException {
        if (promo == null || promoId == null) throw new InvalidException("Promo or ex promo id can not be null");
        Promo exPromo = this.findOne(promoId);
        if (exPromo == null) throw new InvalidException("Could not find promo with id: " + promoId);
        exPromo.setTitle(promo.getTitle());
        exPromo.setDescription(promo.getDescription());
        exPromo.setUrl(promo.getUrl());
        exPromo.setPriority(promo.getPriority());
        exPromo.setTextColor(promo.getTextColor());
        exPromo.setBackgroundColor(promo.getBackgroundColor());
        exPromo.setActive(promo.isActive());
        return this.save(exPromo);
    }

    @Override
    public Promo findOne(Long id) throws NotFoundException {
        Promo promo = this.promoRepo.findById(id).orElse(null);
        if (promo == null) throw new NotFoundException("Could not find promo with id " + id);
        return promo;
    }

    @Override
    public List<Promo> getLatestPromotions() {
        return this.promoRepo.findByActiveOrderByIdDesc(true);
    }

    @Override
    public void notifyUser(Long promoId) throws NotFoundException, ForbiddenException, UnknownException, InvalidException, JsonProcessingException {
        Promo promo = this.promoRepo.findById(promoId).orElse(null);
        if (promo == null) throw new NotFoundException("Cound not find promo with id: " + promoId);
        if (!promo.isActive()) throw new ForbiddenException("Can not notify users. Promotion is not active!");
        NotificationData data = new NotificationData();
        data.setTitle(promo.getTitle());
        String description = promo.getDescription();
        if (description == null) description = "";
        String brief = description.substring(0, Math.min(description.length(), 100));
        data.setMessage(brief);
        if (Promo.Priority.NORMAL.getValue().equals(promo.getPriority()))
            data.setType(PushNotification.Type.PROMOTION.getValue());
        else if (Promo.Priority.HIGH.getValue().equals(promo.getPriority()))
            data.setType(PushNotification.Type.ALERT.getValue());

        PushNotification notification = new PushNotification(null, data);
        notification.setTo("/topics/users");
        this.notificationService.sendNotification(notification);
    }

    @Override
    public void uploadPhoto(Long promoId, MultipartFile multipartFile) throws ImageInvalidException, LimitExceededException, NotFoundException, IOException {
        if (!ImageValidator.isImageValid(multipartFile))
            throw new ImageInvalidException("Invalid image!");
        if (multipartFile.getSize() > 1000000)
            throw new LimitExceededException("Image size should be less than 1 mb");
        Promo promo = this.findOne(promoId);
        UploadProperties properties = this.fileUploadService.uploadFile(multipartFile, UploadProperties.NameSpaces.PROMOTIONS.getValue(), String.valueOf(promo.getId()), -1);
        promo.setImagePath(properties.getFilePath());
        this.promoRepo.save(promo);
    }

}
