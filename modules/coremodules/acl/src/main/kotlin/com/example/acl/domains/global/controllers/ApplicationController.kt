package com.example.acl.domains.global.controllers

import com.example.acl.AclApplication
import com.example.coreweb.commons.Constants
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/application")
@Tag(name = Constants.Swagger.GLOBAL_API, description = Constants.Swagger.GLOBAL_API_DETAILS)
class ApplicationController {

    @PatchMapping("/context/reload")
    @Operation(summary = Constants.Swagger.RELOAD_CONTEXT)
    fun reloadApplicationContext() {
        AclApplication.restart()
    }

}