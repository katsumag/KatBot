package me.katsumag.katbot.extensions

import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity

fun RequestEntity<*>.withDiscordUserAgent(): RequestEntity<Any> {
    val headers = HttpHeaders()
    headers.putAll(this.headers)
    headers.add(HttpHeaders.USER_AGENT, "Discord Bot/KatBot#0828 v0.0.1 - katsumag#7876")
    return RequestEntity(this.body, headers, this.method, this.url)
}