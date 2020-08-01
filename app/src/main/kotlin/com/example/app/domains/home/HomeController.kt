package com.example.app.domains.home

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
@Hidden
class HomeController {

    @GetMapping("")
    fun home(): String {
        return "index"
    }

}