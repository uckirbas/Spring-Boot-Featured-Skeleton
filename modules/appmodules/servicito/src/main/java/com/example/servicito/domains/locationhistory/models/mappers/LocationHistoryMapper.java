package com.example.servicito.domains.locationhistory.models.mappers;


import com.example.auth.config.security.SecurityContext;
import com.example.servicito.domains.locationhistory.models.dtos.LocationHistoryDto;
import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import com.example.auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class LocationHistoryMapper {

    public LocationHistory map(LocationHistoryDto dto, LocationHistory exLocationHistory) {
        LocationHistory locationHistory = exLocationHistory != null ? exLocationHistory : new LocationHistory();

        locationHistory.setLatitude(dto.getLatitude());
        locationHistory.setLongitude(dto.getLongitude());
        locationHistory.setUser(new User(SecurityContext.getCurrentUser()));

        return locationHistory;
    }


    public LocationHistoryDto map(LocationHistory history) {
        LocationHistoryDto dto = new LocationHistoryDto();
        dto.setId(history.getId());
        dto.setCreated(history.getCreatedAt());
        dto.setUpdatedAt(history.getUpdatedAt());

        dto.setName(history.getUser().getName());
        dto.setUsername(history.getUser().getUsername());
        dto.setLatitude(history.getLatitude());
        dto.setLongitude(history.getLongitude());
        dto.setReference(history.getReference());
        dto.setUserId(history.getUser().getId());
        return dto;
    }

}
