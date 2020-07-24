package com.example.servicito.domains.histories.services;

import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.histories.models.entities.RentHistory;
import com.example.servicito.domains.histories.repositories.RentHistoryRepository;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RentHistoryServiceImpl implements RentHistoryService {
    private final RentHistoryRepository rentHistoryRepo;

    @Autowired
    public RentHistoryServiceImpl(RentHistoryRepository rentHistoryRepo) {
        this.rentHistoryRepo = rentHistoryRepo;
    }

    @Override
    public Page<RentHistory> getAll(int page) {
        return this.rentHistoryRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Page<RentHistory> getAllApartmentHistory(Long apartmentId, int page) {
        return this.rentHistoryRepo.findByApartmentId(apartmentId, PageAttr.getPageRequest(page));
    }

    @Override
    public RentHistory getLastByApartment(Long apartmentId) {
        return this.rentHistoryRepo.findFirstByApartmentIdOrderByIdDesc(apartmentId);
    }

    @Override
    public Page<RentHistory> getAllUserHistory(Long userId, int page) {
        return this.rentHistoryRepo.findByUserId(userId, PageAttr.getPageRequest(page));
    }

    @Override
    public RentHistory getLastByUser(Long userId) {
        return this.rentHistoryRepo.findFirstByUserIdOrderByIdDesc(userId);
    }

    @Override
    public RentHistory save(RentHistory history) throws ForbiddenException, InvalidException {
        this.validateRentHistory(history);
        return this.rentHistoryRepo.save(history);
    }

    private void validateRentHistory(RentHistory history) throws ForbiddenException, InvalidException {
        if (history == null) throw new InvalidException("Rent History can not be null!");
        if (history.getUser() == null || history.getApartment() == null)
            throw new ForbiddenException("Could not persist Rent History! User " + history.getUser() + " or Apartment " + history.getApartment() + " is null");
        if (history.getStartDate() == null)
            throw new InvalidException("Starting date needs to be defined! " + history.getStartDate());
    }

    @Override
    public RentHistory getOne(Long id) throws NotFoundException {
        RentHistory history = this.rentHistoryRepo.findById(id).orElse(null);
        if (history == null) throw new NotFoundException("Could not find history with id " + id);
        return history;
    }
}
