package me.katsumag.katbot.extensions

import java.awt.Color
import kotlin.random.Random

fun Color.toHexString(): String {
    return String.format("#%02x%02x%02x", red, green, blue)
}

fun randomColour(): Color {
    return Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
}