package com.noahhendrickson.theboys.extensions

import org.bukkit.Sound
import org.bukkit.entity.HumanEntity

inline fun <T : HumanEntity> T.sendFormattedMessageWithSound(
    sound: Sound,
    volume: Float = 1.0F,
    pitch: Float = 1.0F,
    crossinline message: (T) -> String
) {
    sendMessageFormatted { message(this) }
    world.playSound(location, sound, volume, pitch)
}

fun <T : HumanEntity> T.sendFormattedMessageWithDing(message: (T) -> String) {
    sendFormattedMessageWithSound(Sound.ENTITY_ARROW_HIT_PLAYER, pitch = 1.8F, message = message)
}
