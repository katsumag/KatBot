package me.katsumag.katbot.panel.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PanelController {

    @GetMapping("/panel")
    fun getPanel() = "panel"

}