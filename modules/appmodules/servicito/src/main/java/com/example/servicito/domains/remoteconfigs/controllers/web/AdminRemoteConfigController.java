package com.example.servicito.domains.remoteconfigs.controllers.web;

import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.remoteconfigs.models.entities.RemoteConfig;
import com.example.servicito.domains.remoteconfigs.repositories.RemoteConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/admin/remoteconfigs")
public class AdminRemoteConfigController {
    private final RemoteConfigRepository remoteConfigRepo;

    @Autowired
    public AdminRemoteConfigController(RemoteConfigRepository remoteConfigRepo) {
        this.remoteConfigRepo = remoteConfigRepo;
    }

    @GetMapping("")
    private String getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          Model model) {
        Page<RemoteConfig> configPage = this.remoteConfigRepo.findAll(PageAttr.getPageRequest(page));
        model.addAttribute("configPage", configPage);
        return "adminlte/fragments/remoteconfig/all";
    }

    @GetMapping("/create")
    private String remoteConfigPage() {
        return "adminlte/fragments/remoteconfig/create";
    }

    @PostMapping("/create")
    private String saveRemoteConfig(@ModelAttribute RemoteConfig remoteConfig) {
        if (remoteConfig.getId() != null) {
            RemoteConfig exConfig = this.remoteConfigRepo.findById(remoteConfig.getId()).orElse(null);
            remoteConfig.setCreated(exConfig.getCreated());
            remoteConfig.setLastUpdated(new Date());
        }

        this.remoteConfigRepo.save(remoteConfig);
        return "redirect:/admin/remoteconfigs";
    }


    @GetMapping("/edit/{id}")
    private String editPage(@PathVariable("id") Long configId,
                            Model model) {
        RemoteConfig remoteConfig = this.remoteConfigRepo.findById(configId).orElse(null);
        model.addAttribute("exConfig", remoteConfig);
        return "adminlte/fragments/remoteconfig/create";
    }

    @PostMapping("/delete/{id}")
    private String delete(@PathVariable("id") Long configId) {
        this.remoteConfigRepo.deleteById(configId);
        return "redirect:/admin/remoteconfigs";
    }
}
