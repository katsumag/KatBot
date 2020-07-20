package me.katsumag.katbot.panel.extractors

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

class DiscordPrincipalExtractor : PrincipalExtractor {

    override fun extractPrincipal(map: MutableMap<String, Any>): Any? {
        return map["login"]
    }

}