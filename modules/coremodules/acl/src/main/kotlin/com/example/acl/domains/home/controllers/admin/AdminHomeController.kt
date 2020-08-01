package com.example.acl.domains.home.controllers.admin

import com.example.auth.config.security.SecurityContext
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
@Hidden
class AdminHomeController {

    @GetMapping("/login")
    fun loginPage(): String {
        if (SecurityContext.isAuthenticated())
            return "redirect:/admin/dashboard"
        return "material/pages/login"
    }

    @GetMapping("/admin/dashboard")
    fun dashboard(): String {
        return "material/fragments/dashboard/dashboard"
    }

}
