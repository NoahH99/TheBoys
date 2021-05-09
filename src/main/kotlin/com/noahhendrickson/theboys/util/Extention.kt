package com.noahhendrickson.theboys.util

import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.HumanEntity

fun String.formatColors(altColorChar: Char = '&') =
    ChatColor.translateAlternateColorCodes(altColorChar, this)

inline fun <T : HumanEntity> T.sendFormattedMessageWithSound(
    sound: Sound,
    volume: Float = 1.0F,
    pitch: Float = 1.0F,
    message: T.() -> String
) {
    sendMessage(message(this).formatColors())
    world.playSound(location, sound, volume, pitch)
}

fun <T : HumanEntity> T.sendMessageWithArrowHit(message: T.() -> String) {
    sendFormattedMessageWithSound(Sound.ENTITY_ARROW_HIT_PLAYER, pitch = 1.8F, message = message)
}