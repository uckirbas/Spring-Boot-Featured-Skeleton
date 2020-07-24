package com.example.servicito.domains.locationhistory.services;

import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import com.example.servicito.domains.locationhistory.repositories.LocationHistoryRepository;
import com.example.common.exceptions.invalid.InvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LocationHistoryServiceImpl implements LocationHistoryService {
    private final LocationHistoryRepository lhRepo;

    @Autowired
    public LocationHistoryServiceImpl(LocationHistoryRepository lhRepo) {
        this.lhRepo = lhRepo;
    }

    @Override
    public Page<LocationHistory> search(Long userId, String query, int page, int size) {
        return this.lhRepo.search(userId, query, PageAttr.getPageRequest(page, size));
    }

    @Override
    public LocationHistory save(LocationHistory history) throws InvalidException {
        if (history == null) throw new InvalidException("History can not be null");
        return this.lhRepo.save(history);
    }

    @Override
    public LocationHistory find(Long id) throws InvalidException {
        LocationHistory history = this.lhRepo.findById(id).orElse(null);
        if (history == null) throw new InvalidException("Couldn't find history with id: " + id);
        return history;
    }

    @Override
    public void softDelete(Long id) throws InvalidException {
        LocationHistory history = this.find(id);
        history.setDeleted(true);
        this.lhRepo.save(history);
    }

    @Override
    public Page<LocationHistory> findAll(Integer page) {
        return this.lhRepo.findAll(PageAttr.getPageRequest(page));
    }
}
