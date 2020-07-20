package me.katsumag.katbot.extensions

val links = setOf("https://cdn.katsumag.me/music", "https://www.cdn.katsumag.me/music")

val String.isCDNLink: Boolean
    get() = links.any { startsWith(it) }