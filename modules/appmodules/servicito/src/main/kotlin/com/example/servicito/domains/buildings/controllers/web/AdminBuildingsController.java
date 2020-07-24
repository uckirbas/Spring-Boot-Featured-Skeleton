package com.example.servicito.domains.buildings.controllers.web;//package com.example.servicito.domains.buildings.controllers.web;
//
//import com.example.common.utils.DateUtil;
//import com.example.servicito.domains.buildings.services.BuildingService;
//import com.example.common.exceptions.notfound.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/admin/buildings")
//public class AdminBuildingsController {
//    private final BuildingService buildingService;
//    private final StatsService statsService;
//
//    @Autowired
//    public AdminBuildingsController(BuildingService buildingService, StatsService statsService) {
//        this.buildingService = buildingService;
//        this.statsService = statsService;
//    }
//
//    @GetMapping("")
//    private String allBuildings(@RequestParam(value = "landlord", required = false) String username,
//                                @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                Model model) throws UserNotFoundException {
//        Page buildingPage;
//        if (username == null || username.trim().isEmpty())
//            buildingPage = this.buildingService.getAllBuildingsPaginated(page);
//        else
//            buildingPage = this.buildingService.getBuildingsByLandLord(page, username);
//
//        AdminStats stats = this.statsService.getStatsInAPeriod(DateUtil.getPeriod("this_month"));
//        model.addAttribute("stats", stats);
//
//        model.addAttribute("buildingsPage", buildingPage);
//        model.addAttribute("query", username);
//        return "adminlte/fragments/buildings/all";
//    }
//}
