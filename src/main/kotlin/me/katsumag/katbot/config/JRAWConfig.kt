package me.katsumag.katbot.config

import org.apache.http.auth.UsernamePasswordCredentials
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix="jraw")
class JRAWConfig {

    lateinit var username: String

    lateinit var password: String

    lateinit var clientId: String

    lateinit var clientSecret: String

}