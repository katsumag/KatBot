package me.katsumag.katbot.panel.discord

import me.katsumag.katbot.panel.extractors.DiscordPrincipalExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User

@Configuration
@EnableOAuth2Sso
class DiscordOAuthSecurityConfiguration @Autowired constructor(
        private val responseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>,
        private val userService: OAuth2UserService<OAuth2UserRequest, OAuth2User>
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http.oauth2Login()
                .redirectionEndpoint()
                .baseUri("/panel")
                .and()
                .tokenEndpoint()
                .accessTokenResponseClient(responseClient)
                .and()
                .userInfoEndpoint().userService(userService)

        http.authorizeRequests()
                .antMatchers("/panel", "/panel/**").authenticated()
                .anyRequest().permitAll()

    }

}