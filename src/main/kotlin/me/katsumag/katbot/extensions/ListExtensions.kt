package me.katsumag.katbot.extensions

import net.dv8tion.jda.api.entities.Message

fun List<Any>.sizeEquals(size: Int): Boolean {
    return this.size == size
}

