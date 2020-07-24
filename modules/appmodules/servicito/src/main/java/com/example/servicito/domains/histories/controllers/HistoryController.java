package com.example.servicito.domains.histories.controllers;

import com.example.auth.config.security.SecurityContext;
import com.example.servicito.domains.histories.models.entities.RentHistory;
import com.example.servicito.domains.histories.services.RentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/histories")
public class HistoryController {

    private final RentHistoryService historyService;

    @Autowired
    public HistoryController(RentHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("")
    private ResponseEntity allByUser(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        Page<RentHistory> historyPage = this.historyService.getAllUserHistory(SecurityContext.getCurrentUser().getId(), Math.abs(page));
        return ResponseEntity.ok(historyPage);
    }

    @GetMapping("/apartments/{apartmentId}")
    private ResponseEntity allByApartment(@PathVariable("apartmentId") Long apartmentId,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Page<RentHistory> historyPage = this.historyService.getAllApartmentHistory(apartmentId, Math.abs(page));
        return ResponseEntity.ok(historyPage);
    }

}
