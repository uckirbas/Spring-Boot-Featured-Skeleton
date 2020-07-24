package com.example.servicito.domains.remoteconfigs.controllers;

import com.example.servicito.domains.remoteconfigs.models.entities.RemoteConfig;
import com.example.servicito.domains.remoteconfigs.repositories.RemoteConfigRepository;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/remoteconfigs")
public class RemoteConfigController {
    private final RemoteConfigRepository configRepo;

    @Autowired
    public RemoteConfigController(RemoteConfigRepository configRepo) {
        this.configRepo = configRepo;
    }

    @GetMapping("/self")
    private ResponseEntity getConfig(@RequestParam("package") String packageName) throws NotFoundException {
        RemoteConfig config = this.configRepo.findByAppPackage(packageName);
        if (config == null) throw new NotFoundException("Could not find config for package:" + packageName);
        return ResponseEntity.ok(config);
    }

}
