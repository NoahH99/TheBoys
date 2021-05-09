package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.util.sendMessageWithArrowHit
import org.bukkit.entity.Player
import java.util.*

class CancelCommand(plugin: TheBoys) : BaseTransportationCommand(plugin) {

    override fun BaseCommand.onCommand() {
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

        sendMessageWithArrowHit { "&aYou have canceled ${language.first} transportation request." }

        uuid.toPlayerOrNull()?.sendMessageWithArrowHit {
            "&a${this@sendCancellationMessage.name} has canceled ${language.second} transportation request."
        }
    }
}