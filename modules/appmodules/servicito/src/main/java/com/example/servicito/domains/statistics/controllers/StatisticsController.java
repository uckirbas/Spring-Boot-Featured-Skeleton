package com.example.servicito.domains.statistics.controllers;

import com.example.common.utils.DateUtil;
import com.example.servicito.domains.apartments.repositories.ApartmentRepository;
import com.example.servicito.domains.buildings.repositories.BuildingRepository;
import com.example.auth.annotations.CurrentUser;
import com.example.servicito.domains.complains.repositories.ComplainRepository;
import com.example.servicito.domains.requests.repositories.MRequestRepository;
import com.example.servicito.domains.statistics.models.dtos.Statistics;
import com.example.auth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stats")
public class StatisticsController {

    private final ApartmentRepository apartmentRepo;
    private final BuildingRepository buildingRepo;
    private final MRequestRepository requestRepo;
    private final ComplainRepository complainRepo;

    @Autowired
    public StatisticsController(ApartmentRepository apartmentRepo, BuildingRepository buildingRepo, MRequestRepository requestRepo, ComplainRepository complainRepo) {
        this.apartmentRepo = apartmentRepo;
        this.buildingRepo = buildingRepo;
        this.requestRepo = requestRepo;
        this.complainRepo = complainRepo;
    }


    @GetMapping("")
    private ResponseEntity getStats(@CurrentUser User currentUser) {
        Statistics stats = new Statistics();
        stats.setNoOfApartments(this.apartmentRepo.countByLandlord(currentUser.getId()));
        stats.setNoOfAvailableApartments(this.apartmentRepo.countAvailableByLandlord(currentUser.getId(), true));
        stats.setNoOfBuildings(this.buildingRepo.count(currentUser.getId()));

        stats.setNoOfRequests(this.queryRequestsMap(currentUser));
        stats.setComplains(this.queryComplainsMap(currentUser));
        stats.setApartmentVisits(this.apartmentRepo.findApartmentStats(currentUser.getId()));

        return ResponseEntity.ok(stats);
    }

    private Map<String, Integer> queryRequestsMap(User currentUser) {

        Map<String, Integer> requestsMap = new HashMap<>();
        for (DateUtil.Period period : DateUtil.Period.values()) {
            Map<DateUtil.DateRangeType, Calendar> dateRange = DateUtil.buildDateRange(period);
            Date fromDate = dateRange.get(DateUtil.DateRangeType.DATE_FROM).getTime();
            Date toDate = dateRange.get(DateUtil.DateRangeType.DATE_TO).getTime();
            requestsMap.put(period.toString().toLowerCase(), this.requestRepo.countByApartmentBuildingLandlordIdAndCreatedBetween(currentUser.getId(), fromDate, toDate));
        }
        return requestsMap;
    }


    private Map<String, Integer> queryComplainsMap(User currentUser) {

        Map<String, Integer> complainsMap = new HashMap<>();
        for (DateUtil.Period period : DateUtil.Period.values()) {
            Map<DateUtil.DateRangeType, Calendar> dateRange = DateUtil.buildDateRange(period);
            Date fromDate = dateRange.get(DateUtil.DateRangeType.DATE_FROM).getTime();
            Date toDate = dateRange.get(DateUtil.DateRangeType.DATE_TO).getTime();
            complainsMap.put(period.toString().toLowerCase(), this.complainRepo.countByApartmentBuildingLandlordIdAndCreatedBetween(currentUser.getId(), fromDate, toDate));
        }
        return complainsMap;
    }

}
