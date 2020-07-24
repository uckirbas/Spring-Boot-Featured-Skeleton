package com.example.servicito.domains.statistics.services;

import com.example.common.utils.DateUtil;
import com.example.servicito.domains.activities.repositories.ActivityRepository;
import com.example.servicito.domains.apartments.repositories.ApartmentRepository;
import com.example.servicito.domains.buildings.repositories.BuildingRepository;
import com.example.servicito.domains.orders.repositories.OrderRepository;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.servicito.domains.requests.repositories.MRequestRepository;
import com.example.servicito.domains.statistics.models.dtos.AdminStats;
import com.example.servicito.domains.statistics.models.dtos.EmployeeStats;
import com.example.servicito.domains.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatsServiceImpl implements StatsService {
    private final BuildingRepository buildingRepo;
    private final ApartmentRepository apartmentRepo;
    private final MRequestRepository requestRepo;
    private final UserRepository userRepo;
    private final ActivityRepository activityRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public StatsServiceImpl(BuildingRepository buildingRepo, ApartmentRepository apartmentRepo, MRequestRepository requestRepo, UserRepository userRepo, ActivityRepository activityRepo, OrderRepository orderRepo) {
        this.buildingRepo = buildingRepo;
        this.apartmentRepo = apartmentRepo;
        this.requestRepo = requestRepo;
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public EmployeeStats getEmployeeStates(String username, DateUtil.Period period) {
        Map<DateUtil.DateRangeType, Calendar> dateRange = DateUtil.buildDateRange(period);
        Date fromDate = dateRange.get(DateUtil.DateRangeType.DATE_FROM).getTime();
        Date toDate = dateRange.get(DateUtil.DateRangeType.DATE_TO).getTime();
        int buildingCount = this.buildingRepo.countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username, fromDate, toDate);
        int apartmentCount = this.apartmentRepo.countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username, fromDate, toDate);
        int handledRequestCount = this.requestRepo.countByAssignedEmployeeUsernameAndStatusAndLastUpdatedBetween(username, MRequest.Status.APPOINTMENT_FIXED.getValue(), fromDate, toDate);
        int successRequestCount = this.requestRepo.countByAssignedEmployeeUsernameAndStatusAndLastUpdatedBetween(username, MRequest.Status.CONFIRMED.getValue(), fromDate, toDate);
        int totalRequestCount = handledRequestCount + successRequestCount;


        EmployeeStats employeeStats = new EmployeeStats(buildingCount, apartmentCount, totalRequestCount, successRequestCount);

        if (period != DateUtil.Period.ALL_TIME)  // that will be a disaster
            employeeStats = this.populateInfoCollectedMap(employeeStats, username, fromDate, toDate);

        return employeeStats;
    }

    @Override
    public AdminStats getStatsInAPeriod(DateUtil.Period period) {
        Map<DateUtil.DateRangeType, Calendar> dateRange = DateUtil.buildDateRange(period);
        Date fromDate = dateRange.get(DateUtil.DateRangeType.DATE_FROM).getTime();
        Date toDate = dateRange.get(DateUtil.DateRangeType.DATE_TO).getTime();

        System.out.println("FROM DATE: "+fromDate.toString());
        System.out.println("TO DATE: "+toDate.toString());
        if (period == DateUtil.Period.ALL_TIME)  // that will be a disaster
            return new AdminStats();
        return this.populateAdminStats(fromDate, toDate);
    }

    private EmployeeStats populateInfoCollectedMap(EmployeeStats employeeStats, String username, Date fromDate, Date toDate) {
        Map<Date, Byte> buildingCountMap = new TreeMap<>();
        Map<Date, Byte> apartmentCountMap = new TreeMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(toDate);

        while (calendar.before(endCalendar)) {
            Date date = calendar.getTime();

            Date dayStart = DateUtil.getDayStart(date);
            Date dayEnd = DateUtil.getDayEnd(date);
            int infoCount = this.buildingRepo.countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username, dayStart, dayEnd);
            buildingCountMap.put(date, (byte) infoCount);
            infoCount = this.apartmentRepo.countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username, dayStart, dayEnd);
            apartmentCountMap.put(date, (byte) infoCount);

            calendar.add(Calendar.DATE, 1);
        }
        employeeStats.setBuildingCountMap(buildingCountMap);
        employeeStats.setApartmentCountMap(apartmentCountMap);
        return employeeStats;
    }

    private AdminStats populateAdminStats(Date fromDate, Date toDate) {
        AdminStats stats = new AdminStats();
        Map<Date, Long> usersMap = new TreeMap<>();
        Map<Date, Integer> requestsMap = new TreeMap<>();
        Map<Date, Integer> confirmedRequestsMap = new TreeMap<>();
        Map<Date, Integer> buildingMap = new TreeMap<>();
        Map<Date, Integer> apartmentMap = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(toDate);

        while (calendar.before(endCalendar)) {
            Date date = calendar.getTime();

            Date dayStart = DateUtil.getDayStart(date);
            Date dayEnd = DateUtil.getDayEnd(date);

            Long usersCount = this.userRepo.countByCreatedBetween(dayStart, dayEnd);
            int requestsCount = this.requestRepo.countByCreatedBetween(dayStart, dayEnd);
            int confirmedRequestsCount = this.requestRepo.countByStatusAndCreatedBetween(MRequest.Status.CONFIRMED.getValue(), dayStart, dayEnd);
            int buildingCount = this.buildingRepo.countByCreatedBetween(dayStart, dayEnd);
            int apartmentCount = this.apartmentRepo.countByCreatedBetween(dayStart, dayEnd);

            usersMap.put(date, usersCount);
            requestsMap.put(date, requestsCount);
            confirmedRequestsMap.put(date, confirmedRequestsCount);
            buildingMap.put(date, buildingCount);
            apartmentMap.put(date, apartmentCount);
            calendar.add(Calendar.DATE, 1);
        }
        stats.setNoOfUsers(usersMap);
        stats.setNoOfRequests(requestsMap);
        stats.setNoOfConfirmedRequests(confirmedRequestsMap);
        stats.setNoOfBuildings(buildingMap);
        stats.setNoOfApartments(apartmentMap);
        stats.setNoOfHits(this.activityRepo.countByCreatedBetween(fromDate, toDate));
        stats.setNoOfOrders(this.orderRepo.countByCreatedBetween(fromDate, toDate));
        stats.setTotalUsers(this.userRepo.count());
        stats.setTotalBuildings(this.buildingRepo.count());
        stats.setTotalRequests(this.requestRepo.count());
        stats.setTotalHits(this.activityRepo.count());
        stats.setDailyHits(this.activityRepo.countByCreatedBetween(DateUtil.getDayStart(new Date()), DateUtil.getDayEnd(new Date())));
        return stats;
    }
}
