package com.example.servicito.domains.statistics.controllers;

import com.example.common.utils.DateUtil;
import com.example.servicito.domains.statistics.models.dtos.AdminStats;
import com.example.servicito.domains.statistics.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/stats")
public class StatsAdminController {
    private final StatsService statsService;

    @Autowired
    public StatsAdminController(StatsService statsService) {
        this.statsService = statsService;
    }


    @GetMapping("")
    private ResponseEntity getStats(@RequestParam("period") String period) {
        AdminStats stats = this.statsService.getStatsInAPeriod(DateUtil.getPeriod(period));
        return ResponseEntity.ok(stats);
    }

}
