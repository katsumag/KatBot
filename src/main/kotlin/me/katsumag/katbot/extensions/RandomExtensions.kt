package me.katsumag.katbot.extensions

import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

fun Random.nextHexValue(): String {
    return String.format("#%06x", nextInt(0xffffff + 1))
}