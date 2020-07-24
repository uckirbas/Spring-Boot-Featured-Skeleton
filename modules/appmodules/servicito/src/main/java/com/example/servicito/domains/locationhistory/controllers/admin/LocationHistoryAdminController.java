package com.example.servicito.domains.locationhistory.controllers.admin;

import com.example.servicito.domains.locationhistory.models.dtos.LocationHistoryDto;
import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import com.example.servicito.domains.locationhistory.models.mappers.LocationHistoryMapper;
import com.example.servicito.domains.locationhistory.services.LocationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/location_histories")
public class LocationHistoryAdminController {
    private final LocationHistoryService locationHistoryService;
    private final LocationHistoryMapper lhMapper;

    @Autowired
    public LocationHistoryAdminController(LocationHistoryService locationHistoryService, LocationHistoryMapper lhMapper) {
        this.locationHistoryService = locationHistoryService;
        this.lhMapper = lhMapper;
    }

    @GetMapping("")
    private ResponseEntity<Page<LocationHistoryDto>> search(@RequestParam(value = "user_id", required = false) Long userId,
                                                            @RequestParam(value = "q", defaultValue = "") String query,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "50") int size) {
        Page<LocationHistory> lhs = this.locationHistoryService.search(userId, query, page, size);
        return ResponseEntity.ok(lhs.map(this.lhMapper::map));
    }

}
