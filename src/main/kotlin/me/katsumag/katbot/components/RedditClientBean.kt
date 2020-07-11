package me.katsumag.katbot.components

import me.katsumag.katbot.config.JRAWConfig
import net.dean.jraw.RedditClient
import net.dean.jraw.http.OkHttpNetworkAdapter
import net.dean.jraw.http.UserAgent
import net.dean.jraw.oauth.Credentials
import net.dean.jraw.oauth.OAuthHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RedditClientBean @Autowired constructor(private val config: JRAWConfig){

    @Bean
    fun redditClient(): RedditClient {
        val creds = Credentials.script(config.username, config.password, config.clientId, config.clientSecret)
        val userAgent = UserAgent("bot", "me.katsumag.katbot", "0.0.1", "u/katsumag")
        return OAuthHelper.automatic(OkHttpNetworkAdapter(userAgent), creds)
    }

}