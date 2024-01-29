package com.sber.primechecker.controller

import com.sber.primechecker.entity.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GreetingController {
    /**
     * Handler method to handle starting page request
     */
    @GetMapping("/")
    fun greeting(@AuthenticationPrincipal user: User?, model: Model): String {
        if (user != null) {
            setNavbarModel(user, model)
        } else {
            model.addAttribute("name", "World")
            model.addAttribute("isAuth", false)
            model.addAttribute("isAdmin", false)
        }
        return "greeting"
    }

    private fun setNavbarModel(user: User, model: Model) {
        model.addAttribute("name", user.name)
        model.addAttribute("isAuth", true)
        model.addAttribute("isAdmin", user.isAdmin())
    }
}