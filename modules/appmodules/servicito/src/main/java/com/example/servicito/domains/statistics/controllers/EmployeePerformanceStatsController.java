package com.example.servicito.domains.statistics.controllers;


import com.example.common.utils.DateUtil;
import com.example.servicito.domains.statistics.models.dtos.EmployeeStats;
import com.example.servicito.domains.statistics.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/stats/employee")
public class EmployeePerformanceStatsController {
    private final StatsService employeeStatsService;


    @Autowired
    public EmployeePerformanceStatsController(StatsService employeeStatsService) {
        this.employeeStatsService = employeeStatsService;
    }

    @GetMapping("/fw/{username}")
    private ResponseEntity getFieldEmployeeStats(@PathVariable("username") String username,
                                                 @RequestParam("period") String period) {
        EmployeeStats employeeStats = this.employeeStatsService.getEmployeeStates(username, DateUtil.getPeriod(period));
        return ResponseEntity.ok(employeeStats);
    }


}
