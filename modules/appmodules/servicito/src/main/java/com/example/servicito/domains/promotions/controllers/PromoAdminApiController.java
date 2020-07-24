package com.example.servicito.domains.promotions.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.promotions.models.entities.Promo;
import com.example.servicito.domains.promotions.services.PromoService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.ImageInvalidException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import java.io.IOException;

@Controller
@RequestMapping("/api/v1/admin/promos")
public class PromoAdminApiController {

    private final PromoService promoService;

    @Autowired
    public PromoAdminApiController(PromoService promoService) {
        this.promoService = promoService;
    }

    @GetMapping("")
    private ResponseEntity getAllPromotions(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.promoService.findAll(page));
    }

    // Create promotion // deactivated by default for security purpose
    @PostMapping("")
    private ResponseEntity createPromo(@RequestBody Promo promo) throws InvalidException {
        if (promo != null) promo.setActive(false);
        return ResponseEntity.ok(this.promoService.save(promo));
    }

    @PatchMapping("")
    private ResponseEntity updatePromo(@RequestBody Promo promo) throws InvalidException, NotFoundException {
        if (promo == null || promo.getId() == null) return ResponseEntity.badRequest().build();
        promo = this.promoService.update(promo.getId(), promo);
        return ResponseEntity.ok(promo);
    }

    // activate-deactivate a promotion
    @PatchMapping("/{id}/toggleActivation")
    private ResponseEntity activatePromo(@PathVariable("id") Long id) throws InvalidException, NotFoundException {
        Promo exPromo = this.promoService.findOne(id);
        if (exPromo == null) return ResponseEntity.noContent().build();
        exPromo.setActive(!exPromo.isActive());
        exPromo = this.promoService.save(exPromo);
        return ResponseEntity.ok(exPromo);
    }


    @PostMapping("/{id}/notifyUsers")
    @ResponseStatus(HttpStatus.OK)
    protected void sendNotification(@PathVariable("id") Long promoId) throws JsonProcessingException, InvalidException, ForbiddenException, NotFoundException, UnknownException {
        this.promoService.notifyUser(promoId);
    }

    @PutMapping("/{id}/images/upload")
    private ResponseEntity uploadImage(@PathVariable("id") Long promoId,
                                       @RequestParam("image") MultipartFile multipartFile) throws ImageInvalidException, LimitExceededException, NotFoundException, IOException, ForbiddenException {
        this.promoService.uploadPhoto(promoId, multipartFile);
        return ResponseEntity.ok().build();
    }
}
