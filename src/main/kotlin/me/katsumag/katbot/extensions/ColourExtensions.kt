package me.katsumag.katbot.extensions

import java.awt.Color

fun Color.toHexString(): String {
    return String.format("#%02x%02x%02x", red, green, blue)
}