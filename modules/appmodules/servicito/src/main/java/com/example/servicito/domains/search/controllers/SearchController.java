package com.example.servicito.domains.search.controllers;//package com.example.servicito.domains.search.controllers;
//
//
//import com.example.servicito.domains.address.models.entities.LatLng;
//import com.example.servicito.domains.apartments.services.ApartmentService;
//import com.example.common.exceptions.notfound.NotFoundException;
//import com.example.servicito.domains.users.services.ProfileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/v1/search")
//public class SearchController {
//    private final ApartmentService apartmentService;
//    private final ProfileService profileService;
//
//    @Autowired
//    public SearchController(ApartmentService apartmentService, ProfileService profileService) {
//        this.apartmentService = apartmentService;
//        this.profileService = profileService;
//    }
//
//    @GetMapping("")
//    private ResponseEntity getApartmentLatLong(@RequestParam(value = "area", defaultValue = "dhaka") String area,
//                                               @RequestParam(value = "lat", required = false) Double lat,
//                                               @RequestParam(value = "lng", required = false) Double lng,
//                                               @RequestParam(value = "bachelor", defaultValue = "false") boolean bachelorAllowed,
//                                               @RequestParam(value = "bed", required = false) Integer noOfBed,
//                                               @RequestParam(value = "rentFrom", required = false) Integer rentFrom,
//                                               @RequestParam(value = "rentTo", required = false) Integer rentTo
//    ) throws IOException, NotFoundException {
//        if (lat == null || lng == null) {
//            if (noOfBed != null && rentFrom != null)
//                return ResponseEntity.ok(this.apartmentService.filterLatLongByArea(area, bachelorAllowed, noOfBed, rentFrom, rentTo));
//            return ResponseEntity.ok(this.apartmentService.getLatLongByArea(area, bachelorAllowed));
//        }
//        return ResponseEntity.ok(this.apartmentService.getNearbyLatLongs(new LatLng(lat, lng), bachelorAllowed));
//    }
//
//    @GetMapping("/donors")
//    private ResponseEntity getBloodDonorLatLong(@RequestParam(value = "area", defaultValue = "dhaka") String area,
//                                               @RequestParam(value = "lat", required = false)Double lat,
//                                               @RequestParam(value = "lng", required = false) Double lng,
//                                               @RequestParam(value = "bloodGroup", defaultValue = "") String bloodGroup
//    ) throws IOException, NotFoundException {
//        if(lat == null || lng == null)
//            return ResponseEntity.ok(this.profileService.getDonorsLatLongByArea(area, bloodGroup));
//
//        return ResponseEntity.ok(this.profileService.getDonorsNearbyLatLongs(new LatLng(lat, lng), bloodGroup));
//    }
//
//
//}
