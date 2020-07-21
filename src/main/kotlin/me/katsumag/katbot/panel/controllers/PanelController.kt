package me.katsumag.katbot.panel.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class PanelController {

    @GetMapping("/panel")
    fun getPanel() = "panel"

    @GetMapping("/login/oauth2/code/discord")
    fun whenDiscordLoginDone(@RequestParam("code") code: String?) = "redirect:/panel"

}