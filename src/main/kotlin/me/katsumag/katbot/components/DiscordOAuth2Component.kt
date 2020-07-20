package me.katsumag.katbot.components

import me.katsumag.katbot.extensions.withDiscordUserAgent
import me.katsumag.katbot.panel.extractors.DiscordPrincipalExtractor
import org.springframework.context.annotation.Bean
import org.springframework.http.RequestEntity
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component


@Component
class DiscordOAuth2Component {

    @Bean
    fun accessTokenResponseClient(): OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest?>? {
        val client = DefaultAuthorizationCodeTokenResponseClient()
        client.setRequestEntityConverter(object : OAuth2AuthorizationCodeGrantRequestEntityConverter() {
            override fun convert(oauth2Request: OAuth2AuthorizationCodeGrantRequest): RequestEntity<*>? {
                return super.convert(oauth2Request)?.withDiscordUserAgent()
            }
        })
        return client
    }

    @Bean
    fun userService(): OAuth2UserService<OAuth2UserRequest?, OAuth2User?>? {
        val service = DefaultOAuth2UserService()
        service.setRequestEntityConverter(object : OAuth2UserRequestEntityConverter() {
            override fun convert(userRequest: OAuth2UserRequest): RequestEntity<*>? {
                return super.convert(userRequest)?.withDiscordUserAgent()
            }
        })
        return service
    }

    @Bean
    fun discordPrincipleExtractor(): DiscordPrincipalExtractor {
        return DiscordPrincipalExtractor()
    }

}