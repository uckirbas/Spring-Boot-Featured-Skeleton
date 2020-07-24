package com.example.servicito.domains.locationhistory.services;

import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import com.example.common.exceptions.invalid.InvalidException;
import org.springframework.data.domain.Page;

public interface LocationHistoryService {
    Page<LocationHistory> search(Long userId, String query, int page, int size);
    LocationHistory save(LocationHistory history) throws InvalidException;
    LocationHistory find(Long id) throws InvalidException;
    void softDelete(Long id) throws InvalidException;

    Page<LocationHistory> findAll(Integer page);
}
