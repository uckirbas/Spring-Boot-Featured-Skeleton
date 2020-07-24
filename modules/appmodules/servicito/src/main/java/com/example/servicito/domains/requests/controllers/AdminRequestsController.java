package com.example.servicito.domains.requests.controllers;

import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.servicito.domains.requests.services.MRequestService;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/requests")
public class AdminRequestsController {
    private final MRequestService requestService;

    @Autowired
    public AdminRequestsController(MRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("")
    private String getRequests(@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "userType", required = false) String userType,
                               @RequestParam(value = "username", required = false) String username,
                               Model model) throws UserNotFoundException {

        Page<MRequest> requestPage = this.requestService.findByStatusAndUser(status, userType, username, page);

        model.addAttribute("requestPage", requestPage);
        model.addAttribute("status", status);
        model.addAttribute("userType", userType);
        model.addAttribute("username", username);
        return "adminlte/fragments/requests/all";
    }

}
