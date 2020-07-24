package com.example.servicito.domains.search.controllers;


import com.example.servicito.domains.address.models.dto.LatLngDto;
import com.example.servicito.domains.apartments.services.ApartmentService;
import com.example.servicito.domains.search.models.responses.LatLngSearchResponse;
import com.example.servicito.domains.users.services.ProfileService;
import com.example.common.exceptions.notfound.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/search")
public class SearchControllerV2 {
    private final ApartmentService apartmentService;
    private final ProfileService profileService;

    @Autowired
    public SearchControllerV2(ApartmentService apartmentService, ProfileService profileService) {
        this.apartmentService = apartmentService;
        this.profileService = profileService;
    }

    @GetMapping("")
    private ResponseEntity getApartmentLatLong(@RequestParam(value = "area", defaultValue = "dhaka") String area,
                                               @RequestParam(value = "lat", required = false) Double lat,
                                               @RequestParam(value = "lng", required = false) Double lng,
                                               @RequestParam(value = "bachelor", defaultValue = "false") boolean bachelorAllowed,
                                               @RequestParam(value = "bed", required = false) Integer noOfBed,
                                               @RequestParam(value = "rentFrom", required = false) Integer rentFrom,
                                               @RequestParam(value = "rentTo", required = false) Integer rentTo
    ) throws IOException, NotFoundException {
        LatLngSearchResponse searchResult;
        if (lat == null || lng == null) {
            if (noOfBed != null && rentFrom != null)
                searchResult = this.apartmentService.filterLatLongByAreaV2(area, bachelorAllowed, noOfBed, rentFrom, rentTo);
            else searchResult = this.apartmentService.getLatLongByAreaV2(area, bachelorAllowed);
        } else
            searchResult = new LatLngSearchResponse("", new LatLngDto(lat, lng), this.apartmentService.getNearbyLatLongs(new LatLngDto(lat, lng), bachelorAllowed));

        return ResponseEntity.ok(searchResult);
    }


}
