package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.task.TeleportCountdownTask
import com.noahhendrickson.theboys.util.Permissions
import com.noahhendrickson.theboys.Transport
import org.bukkit.command.Command
import org.bukkit.entity.Player

class TransportationCommand(plugin: TheBoys) : BaseTransportationCommand(plugin) {

    override fun BaseCommand.onCommand() {
        if (args.isEmpty()) return sendError { "Please specify a player!" }

        val destination = args[0].toPlayerOrNull() ?: return sendError { "That is not a valid player!" }

        if (player.uniqueId == destination.uniqueId) return sendError { "You cannot transport yourself to yourself." }

        val isAdmin = player.hasPermission(Permissions.ADMIN.node)
        val bypassCounter = args.getOrNull(1).toBoolean()
        val maxCounterValue = if (isAdmin && !bypassCounter) 0 else 15

        TeleportCountdownTask(player, destination, plugin, maxCounterValue)
            .runTaskTimer(plugin, 0, 20).also {
                transportationCountdownTasks.add(Transport(player, destination, it))
            }
    }
}