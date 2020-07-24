package com.example.servicito.domains.activities.services;

import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.activities.models.entities.Activity;
import com.example.servicito.domains.activities.repositories.ActivityRepository;
import com.example.auth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepo;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Activity save(Activity activity) {
        if (activity.getId() == null) { // new activity (user logged in)
            Activity firstActivity = this.findFirst();
            if (firstActivity != null) {
                Long total = firstActivity.getTotalVisitors();
                activity.setTotalVisitors(++total);
                firstActivity.setTotalVisitors(total);
                this.activityRepo.save(firstActivity);
            }
        }
        return this.activityRepo.save(activity);
    }

    @Override
    public Activity findFirst() {
        return this.activityRepo.findFirstBy();
    }

    @Override
    public Activity findLast(User user) {
        return activityRepo.findFirstByUserOrderByIdDesc(user);
    }

    @Override
    public Page<Activity> findAllPaginated(int page) {
        return this.activityRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Page<Activity> findByUser(User user, int page, int size) {
        return this.activityRepo.findByUser(user,  PageRequest.of(page, size, Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID));
    }

    @Override
    public Activity findOne(long id) {
        return this.activityRepo.findById(id).orElse(null);
    }

    @Override
    public List<Activity> findAll() {
        return this.activityRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        this.activityRepo.deleteById(id);
    }

}