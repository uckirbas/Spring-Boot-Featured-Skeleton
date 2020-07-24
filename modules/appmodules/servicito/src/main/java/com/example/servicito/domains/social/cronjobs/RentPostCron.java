package com.example.servicito.domains.social.cronjobs;

import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.apartments.services.ApartmentService;
import com.example.servicito.domains.social.posts.services.PostService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class RentPostCron {

    private final PostService postService;
    private final ApartmentService apartmentService;

    @Autowired
    public RentPostCron(PostService postService, ApartmentService apartmentService) {
        this.postService = postService;
        this.apartmentService = apartmentService;
    }

    @Scheduled(fixedDelay = 3600000)
    public void scheduleFixedDelayTask() throws ForbiddenException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -1);
        List<Apartment> apartments = this.apartmentService.findWithinPeriod(calendar.getTime(), new Date());
        if (apartments.isEmpty()) {
            System.out.println("[New Post For Rent Cron Job] no new apartments last hour.");
            return;
        }
        for (Apartment apartment : apartments) {
            if (apartment.getAvailable())
                this.postService.createPostForNewRent(apartment);
        }
    }

}
