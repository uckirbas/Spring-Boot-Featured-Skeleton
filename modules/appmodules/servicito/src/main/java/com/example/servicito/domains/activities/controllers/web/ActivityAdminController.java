package com.example.servicito.domains.activities.controllers.web;

import com.example.servicito.domains.activities.models.entities.Activity;
import com.example.servicito.domains.activities.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/activities")
public class ActivityAdminController {
    private final ActivityService activityService;

    @Autowired
    public ActivityAdminController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("")
    private String getActivities(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                 Model model) {
        Page<Activity> activities = this.activityService.findAllPaginated(page);
        model.addAttribute("activitiyPage", activities);
        return "material/fragments/activities/all";
    }

}
