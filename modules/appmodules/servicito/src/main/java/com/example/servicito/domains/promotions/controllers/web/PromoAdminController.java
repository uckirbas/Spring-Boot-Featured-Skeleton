package com.example.servicito.domains.promotions.controllers.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.promotions.models.entities.Promo;
import com.example.servicito.domains.promotions.services.PromoService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.LimitExceededException;
import java.io.IOException;

@Controller
@RequestMapping("/admin/promos")
public class PromoAdminController {

    private final PromoService promoService;

    @Autowired
    public PromoAdminController(PromoService promoService) {
        this.promoService = promoService;
    }

    @GetMapping("")
    private String getAllPromotions(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    Model model) {
        Page<Promo> promoPage = this.promoService.findAll(page);

        model.addAttribute("promoPage", promoPage);
        return "adminlte/fragments/promos/all";
    }

    @GetMapping("/create")
    private String createPromoPage() {
        return "adminlte/fragments/promos/create";
    }

    // Create promotion // deactivated by default for security purpose
    @PostMapping("/create")
    private String createPromo(@ModelAttribute Promo promo,
                               @RequestParam("image") MultipartFile multipartFile) throws InvalidException, LimitExceededException, IOException, NotFoundException {
        if (promo == null) throw new InvalidException("Promo can not be null!");
        promo.setActive(false);
        promo = promo.getId() == null ? this.promoService.save(promo) : this.promoService.update(promo.getId(), promo);
        if (multipartFile != null && !multipartFile.isEmpty())
            this.promoService.uploadPhoto(promo.getId(), multipartFile);
        return "redirect:/admin/promos";
    }

    @GetMapping("/edit/{promoId}")
    private String updatePromoPage(@PathVariable("promoId") Long promoId, Model model) throws NotFoundException {
        Promo exPromo = this.promoService.findOne(promoId);
        model.addAttribute("exPromo", exPromo);
        return "adminlte/fragments/promos/create";
    }

    // activate-deactivate a promotion
    @GetMapping("/{id}/toggleActivation")
    private String activatePromo(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) throws InvalidException, NotFoundException {
        Promo exPromo = this.promoService.findOne(id);
        if (exPromo == null) throw new NotFoundException("Could not find promo with id: " + id);
        exPromo.setActive(!exPromo.isActive());
        exPromo = this.promoService.save(exPromo);
        String msg = exPromo.isActive() ? "Successfully activated promotion " + id : "Successfully deactivated promo " + id;
        redirectAttributes.addFlashAttribute("flash", msg);
        return "redirect:/admin/promos";
    }


    @PostMapping("/{id}/notifyUsers")
    protected String sendNotification(@PathVariable("id") Long promoId, RedirectAttributes redirectAttributes) throws JsonProcessingException, InvalidException, ForbiddenException, NotFoundException, UnknownException {
        this.promoService.notifyUser(promoId);
        redirectAttributes.addFlashAttribute("flash", "Push notification sent!");
        return "redirect:/admin/promos";
    }


}
