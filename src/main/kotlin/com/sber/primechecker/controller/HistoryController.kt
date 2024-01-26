package com.sber.primechecker.controller

import com.sber.com.sber.primechecker.dto.NumberCheckDto
import com.sber.primechecker.entity.Algorithm
import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.User
import com.sber.primechecker.service.AlgorithmService
import com.sber.primechecker.service.HistoryService
import com.sber.primechecker.service.PrimeNumberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.concurrent.ExecutionException


@Controller
class HistoryController(
    @Autowired
    private val historyService: HistoryService,

    @Autowired
    private val algorithmService: AlgorithmService,

    @Autowired
    private val primeNumberService: PrimeNumberService
) {


    @GetMapping("/history")
    fun history(
        @AuthenticationPrincipal currentUser: User,
        model: Model,
        @RequestParam("page") pageParam: Int?
    ): String {
        val numberCheckDto = NumberCheckDto()
        setNavbarModel(currentUser, model)

        model.addAttribute("numberCheck", numberCheckDto)

        val page = if (pageParam == null || pageParam < 0) 0 else pageParam
        val pageable: Pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "checkDateTime")
        val historiesByUser: Page<History> = historyService.findByUser(currentUser, pageable)
        val algorithms: List<Algorithm> = algorithmService.findAll()
        model.addAttribute("algos", algorithms)
        if (model.getAttribute("inputmessage") == null) {
            model.addAttribute("inputmessage", "")
        }
        if (historiesByUser.totalElements == 0L) {
            model.addAttribute("hasHistory", false)
        } else {
            model.addAttribute("message", "")
            model.addAttribute("histories", historiesByUser)
            model.addAttribute("hasHistory", true)
        }
        model.addAttribute("page", page)
        return "history"
    }

    @PostMapping("/history")
    @Throws(ExecutionException::class, InterruptedException::class)
    fun addHistory(
        @AuthenticationPrincipal currentUser: User,
        @ModelAttribute("numberCheck") numberCheckDto: NumberCheckDto,
        model: Model, result: BindingResult
    ): String {
        try {
            val number = numberCheckDto.inputNumber?.toLong()
            val iterations = numberCheckDto.inputIterations?.toInt()
            val algorithmName = numberCheckDto.inputAlgorithm
            if (number == null || iterations == null || algorithmName == null) {
                throw NumberFormatException()
            }
            primeNumberService.checkNumber(currentUser, algorithmName, number, iterations)
        } catch (e: NumberFormatException) {
            result.rejectValue("inputNumber", "400", "Wrong data input!")
            if (result.hasErrors()) {
                model.addAttribute("numberCheck", numberCheckDto)
            }
            return "redirect:/history"
        }
        return "redirect:/history"
    }

    private fun setNavbarModel(user: User, model: Model) {
        model.addAttribute("name", user.name)
        model.addAttribute("isAuth", true)
        model.addAttribute("isAdmin", user.isAdmin())
    }
}
