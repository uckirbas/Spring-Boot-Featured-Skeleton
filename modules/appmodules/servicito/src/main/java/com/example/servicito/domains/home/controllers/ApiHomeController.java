package com.example.servicito.domains.home.controllers;//package com.example.servicito.domains.home.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.example.common.utils.DateUtil;
//import com.example.common.utils.NetworkUtil;
//import com.example.auth.config.security.TokenService;
//import com.example.auth.config.security.WebSecurityConfig;
//import com.example.auth.entities.AcValidationToken;
//import com.example.auth.entities.User;
//import com.example.auth.annotations.CurrentUser;
//import com.example.servicito.domains.notifications.models.pojo.NotificationData;
//import com.example.servicito.domains.notifications.models.pojo.PushNotification;
//import com.example.common.exceptions.exists.UserAlreadyExistsException;
//import com.example.common.exceptions.forbidden.ForbiddenException;
//import com.example.common.exceptions.invalid.InvalidException;
//import com.example.common.exceptions.invalid.UserInvalidException;
//import com.example.common.exceptions.notfound.UserNotFoundException;
//import com.example.common.exceptions.nullpointer.NullPasswordException;
//import com.example.common.exceptions.unknown.UnknownException;
//import com.example.servicito.domains.users.services.AcValidationTokenService;
//import com.example.servicito.domains.notifications.services.NotificationService;
//import com.example.acl.domains.users.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//
//@RestController
//public class ApiHomeController {
//
//    private final UserService userService;
//    private final AcValidationTokenService acValidationTokenService;
//    private final NotificationService notificationService;
//    private final TokenService tokenService;
//
//    @Value("${baseUrl}")
//    private String baseUrl;
//
//    @Autowired
//    public ApiHomeController(UserService userService, AcValidationTokenService acValidationTokenService, NotificationService notificationService, TokenService tokenService) {
//        this.userService = userService;
//        this.acValidationTokenService = acValidationTokenService;
//        this.notificationService = notificationService;
//        this.tokenService = tokenService;
//    }
//
//    @PostMapping("/register")
//    private ResponseEntity register(@ModelAttribute User user, BindingResult bindingResult,
//                                    @RequestParam(value = "sendPassword", defaultValue = "false") Boolean sendPassword,
//                                    @CurrentUser User currentUser) throws UserAlreadyExistsException, InvalidException, NullPasswordException, JsonProcessingException, UnknownException {
//        if (bindingResult.hasErrors())
//            return ResponseEntity.badRequest().build();
//        String tempRawPassword = user.getPassword();
//        user = this.userService.save(user);
//
//        // Notify admin for new registration
//        this.notifyAdmin(user);
//
//        // send sms to landlords with their password when field employee adds them
//        if (currentUser != null && !currentUser.isNotAdminOrEmployeeOrFieldEmployee() && sendPassword) {
//            String message = "Dear User, your " + baseUrl + " credentials are - Username: " + user.getUsername() + " and Password: " + tempRawPassword;
//            NetworkUtil.sendSms(user.getUsername(), message);
//        }
//
//        WebSecurityConfig.updateAuthentication(user);
//
//        return ResponseEntity.ok(tokenService.createAccessToken(user));
//    }
//
//    private void notifyAdmin(User user) throws UnknownException, InvalidException, JsonProcessingException {
//        NotificationData data = new NotificationData();
//        data.setTitle("New Registration -:- " + user.getName());
//        String description = "Username: " + user.getUsername() + ", On: " + DateUtil.getReadableDateTime(new Date());
//        String brief = description.substring(0, Math.min(description.length(), 100));
//        data.setMessage(brief);
//        data.setType(PushNotification.Type.ADMIN_NOTIFICATIONS.getValue());
//
//        PushNotification notification = new PushNotification(null, data);
//        notification.setTo("/topics/adminnotifications");
//        this.notificationService.sendNotification(notification);
//    }
//
//    @PostMapping("/changePassword")
//    private ResponseEntity changePassword(@RequestParam("currentPassword") String currentPassword,
//                                          @RequestParam("newPassword") String newPassword,
//                                          @CurrentUser User currentUser) throws UserNotFoundException, NullPasswordException, ForbiddenException, InvalidException {
//        this.userService.changePassword(currentUser.getId(), currentPassword, newPassword);
//        return ResponseEntity.ok().build();
//    }
//
//    // Password reset
//    @GetMapping("/resetPassword")
//    @ResponseStatus(HttpStatus.OK)
//    private void requestResetPassword(@RequestParam("username") String username) throws UserNotFoundException, ForbiddenException, UnknownException {
//        this.userService.handlePasswordResetRequest(username);
//    }
//
//    @PostMapping("/resetPassword")
//    @ResponseStatus(HttpStatus.OK)
//    private void resetPassword(@RequestParam("username") String username,
//                               @RequestParam("token") String token,
//                               @RequestParam("password") String password) throws UserNotFoundException, ForbiddenException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
//        this.userService.resetPassword(username, token, password);
//    }
//
//
//    // Verify email when registration
//    @GetMapping("/register/verify")
//    @Transactional
//    String verifyRegistration(@RequestParam("token") String token) throws Exception, NullPasswordException, UserAlreadyExistsException, UserInvalidException, ForbiddenException {
//        if (!this.acValidationTokenService.isTokenValid(token))
//            return "redirect:" + this.baseUrl + "/login?verify=false";
//        AcValidationToken acValidationToken = this.acValidationTokenService.findByToken(token);
//        if (acValidationToken == null) return "redirect:" + this.baseUrl + "/login?verify=false";
//        User user = acValidationToken.getUser();
//        user.setEnabled(true);
//        this.userService.save(user);
//
//        acValidationToken.setTokenValid(false);
//        acValidationToken.setReason("Registration/Email Confirmation");
//        this.acValidationTokenService.save(acValidationToken);
//        return "redirect:" + this.baseUrl + "/login?verify=true";
//    }
//
//
//}
