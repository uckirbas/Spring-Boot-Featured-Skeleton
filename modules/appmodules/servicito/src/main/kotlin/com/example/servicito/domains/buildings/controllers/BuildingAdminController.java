package com.example.servicito.domains.buildings.controllers;//package com.example.servicito.domains.buildings.controllers;
//
//import com.example.servicito.domains.buildings.services.BuildingService;
//import com.example.common.exceptions.notfound.BuildingNotFoundException;
//import com.example.common.exceptions.notfound.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/admin/buildings")
//public class BuildingAdminController {
//    private final BuildingService buildingService;
//
//    @Autowired
//    public BuildingAdminController(BuildingService buildingService) {
//        this.buildingService = buildingService;
//    }
//
//    @GetMapping("")
//    private ResponseEntity getBuildingsList(@RequestParam(value = "landlordId", required = false) Long landlordId,
//                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws UserNotFoundException {
//        Page buildingPage;
//        if (landlordId == null) buildingPage = this.buildingService.getAllBuildingsPaginated(page);
//        else buildingPage = this.buildingService.getBuildingsByLandLord(page, landlordId);
//
//        return ResponseEntity.ok(buildingPage);
//    }
//
//    @PostMapping("/{id}/verify")
//    private ResponseEntity verifyBuilding(@PathVariable("id") Long buildingId,
//                                          @RequestParam("verified") Boolean verified) throws BuildingNotFoundException {
//        this.buildingService.verifyBuilding(buildingId, verified);
//        return ResponseEntity.ok().build();
//    }
//}
