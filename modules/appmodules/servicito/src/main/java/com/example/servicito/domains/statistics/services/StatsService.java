package com.example.servicito.domains.statistics.services;

import com.example.common.utils.DateUtil;
import com.example.servicito.domains.statistics.models.dtos.AdminStats;
import com.example.servicito.domains.statistics.models.dtos.EmployeeStats;

public interface StatsService {
    EmployeeStats getEmployeeStates(String username, DateUtil.Period period);

    AdminStats getStatsInAPeriod(DateUtil.Period period);
}
