package com.noahhendrickson.theboys.command.impl

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.command.PlayerCommandContext
import com.noahhendrickson.theboys.command.TransportationCommand
import com.noahhendrickson.theboys.extensions.sendFormattedMessageWithDing
import org.bukkit.entity.Player
import java.util.*

class CancelCommand(override val plugin: TheBoys) : TransportationCommand {

    override fun PlayerCommandContext.onCommand() {
        val uuid = player.uniqueId
        val transport = transportationCountdownTasks
            .firstOrNull { it.transporterUUID == uuid || it.destinationUUID == uuid }
            ?: return sendError { "&cYou do not have any pending transportation requests." }

        with(transport) {
            cancel()
            transportationCountdownTasks.remove(this)

            when {
                transporterUUID == uuid -> player.sendCancellationMessage(destinationUUID, true)
                destinationUUID == uuid -> player.sendCancellationMessage(transporterUUID, false, transporterName)
                else -> null
            }
        }
    }

    private fun Player.sendCancellationMessage(uuid: UUID, forSelf: Boolean, optionalTransporterName: String = "") {
        val language = if (forSelf) "your" to "their" else "$optionalTransporterName's" to "your"

        sendFormattedMessageWithDing { "&aYou have canceled ${language.first} transportation request." }

        uuid.toPlayerOrNull()?.sendFormattedMessageWithDing {
            "&a${name} has canceled ${language.second} transportation request."
        }
    }
}
