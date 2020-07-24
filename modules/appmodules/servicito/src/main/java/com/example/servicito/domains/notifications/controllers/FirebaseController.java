package com.example.servicito.domains.notifications.controllers;

import com.example.auth.annotations.CurrentUser;
import com.example.servicito.domains.notifications.services.FirebaseTokenService;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.auth.entities.User;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/v1/firebase")
public class FirebaseController {
    private final FirebaseTokenService firebaseTokenService;
    private final NotificationService notificationService;

    @Autowired
    public FirebaseController(FirebaseTokenService firebaseTokenService, NotificationService notificationService) {
        this.firebaseTokenService = firebaseTokenService;
        this.notificationService = notificationService;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    private void saveToken(@CurrentUser User user,
                           @RequestParam("token") String token) throws UserNotFoundException, InvalidException {
        this.firebaseTokenService.save(user.getId(), token);
    }

}
