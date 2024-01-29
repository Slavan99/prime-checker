package com.sber.primechecker.controller

import com.sber.primechecker.entity.Role
import com.sber.primechecker.entity.User
import com.sber.primechecker.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AdminController(
    @Autowired
    private val userService: UserService
) {

    /**
     * Handler method to get admin form
     */
    @GetMapping("/admin")
    fun admin(@AuthenticationPrincipal currentUser: User, model: Model): String {
        model.addAttribute("username", currentUser.name)
        model.addAttribute("isAdmin", currentUser.isAdmin())
        model.addAttribute("isAuth", true)
        if (!currentUser.authorities.contains(Role.ADMIN)) {
            return "redirect:/"
        }
        val users: Iterable<User> = userService.findAll()
        model.addAttribute("users", users)
        return "admin"
    }
    /**
     * Handler method handle admin form submit request
     */
    @PostMapping("/admin")
    fun postUser(
        @AuthenticationPrincipal currentUser: User, @RequestParam user_name: String?,
        @RequestParam active: Boolean, @RequestParam admin: Boolean
    ): String {
        if (currentUser.authorities.contains(Role.ADMIN)) {
            userService.handleUserActiveAndAdmin(user_name, active, admin)
        }
        return "redirect:/admin"
    }
}
