package com.example.servicito.domains.histories.services;

import com.example.servicito.domains.histories.models.entities.RentHistory;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

public interface RentHistoryService {
    Page<RentHistory> getAll(int page);

    Page<RentHistory> getAllApartmentHistory(Long apartmentId, int page);

    RentHistory getLastByApartment(Long apartmentId);

    Page<RentHistory> getAllUserHistory(Long userId, int page);

    RentHistory getLastByUser(Long userId);

    RentHistory save(RentHistory history) throws ForbiddenException, InvalidException;

    RentHistory getOne(Long id) throws NotFoundException;
}
