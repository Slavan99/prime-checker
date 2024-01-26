package com.sber.com.sber.primechecker.controller

import com.sber.com.sber.primechecker.dto.UserDto
import com.sber.com.sber.primechecker.exception.IncorrectDataException
import com.sber.com.sber.primechecker.exception.UserAlreadyExistAuthenticationException
import com.sber.primechecker.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class RegistrationController(
    @Autowired
    private val userService: UserService
) {

    // handler method to handle user registration form request
    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        // create model object to store form data
        val user = UserDto()
        model.addAttribute("user", user)
        return "register"
    }


    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    fun registration(
        @ModelAttribute("user") userDto: UserDto,
        result: BindingResult,
        model: Model
    ): String {
        try {
            userService.registerUser(userDto)
        } catch (e: Exception) {
            when (e) {
                is IncorrectDataException, is UserAlreadyExistAuthenticationException -> {
                    result.rejectValue(
                        "name", "400",
                        e.message
                    )
                }
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto)
            return "/register"
        }

        return "redirect:/register?success"
    }

}
