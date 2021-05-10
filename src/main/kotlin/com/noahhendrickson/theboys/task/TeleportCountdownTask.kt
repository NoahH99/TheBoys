package com.noahhendrickson.theboys.task

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.extensions.sendFormattedMessageWithDing
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class TeleportCountdownTask(
    private val transporter: Player,
    private val destination: Player,
    private val plugin: TheBoys,
    private val maxCounterValue: Int
) : BukkitRunnable() {

    private var counter = maxCounterValue

    private val counterFormatted: String
        get() {
            val colorCode = when (counter) {
                in 0..5 -> "c"
                in 5..10 -> "6"
                else -> "e"
            }

            return "&$colorCode$counter"
        }

    private fun init() {
        destination.sendFormattedMessageWithDing {
            "&c${transporter.name} will be transported to your location in $counter seconds.\n" +
                    "To cancel this, run the command: /cancel"
        }
    }

    override fun run() {
        if (counter == maxCounterValue && maxCounterValue != 0) init()

        if (counter > 0) {
            if (counter == maxCounterValue || counter == 10 || counter <= 5)
                transporter.sendFormattedMessageWithDing { "&aTransportation will occur in $counterFormatted &aseconds." }

            counter--
        } else {
            transporter.teleport(destination.location)
            sendInfoMessages()
            plugin.transportationCountdownTasks.removeIf { it.transporterUUID == transporter.uniqueId }
            cancel()
        }
    }

    private fun sendInfoMessages() {
        transporter.sendFormattedMessageWithDing { "&aYou have been transported to ${destination.name}." }
        destination.sendFormattedMessageWithDing { "&a${transporter.name} has been transported to you." }
    }
}
