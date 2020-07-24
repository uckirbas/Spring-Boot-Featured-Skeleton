package com.example.servicito.domains.locationhistory.controllers;

import com.example.servicito.domains.locationhistory.models.dtos.LocationHistoryDto;
import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import com.example.servicito.domains.locationhistory.models.mappers.LocationHistoryMapper;
import com.example.servicito.domains.locationhistory.services.LocationHistoryService;
import com.example.common.exceptions.invalid.InvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/location_histories")
public class LocationHistoryController {

    private final LocationHistoryMapper lhMapper;
    private final LocationHistoryService lhService;

    @Autowired
    public LocationHistoryController(LocationHistoryMapper lhMapper, LocationHistoryService lhService) {
        this.lhMapper = lhMapper;
        this.lhService = lhService;
    }

    @PostMapping("")
    private ResponseEntity create(@RequestBody LocationHistoryDto dto) throws InvalidException {
        LocationHistory history = this.lhService.save(this.lhMapper.map(dto, null));
        return ResponseEntity.created(URI.create("/api/v1/location_histories/" + history.getId())).build();
    }


}
