package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.extensions.sendMessageFormatted
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

interface PlayerCommand : CommandExecutor {

    val plugin: TheBoys

    fun String.toPlayerOrNull() = plugin.server.getPlayer(this)

    fun UUID.toPlayerOrNull() = plugin.server.getPlayer(this)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessageFormatted { "&cOnly players can execute this command." }
            return true
        }

        PlayerCommandContext(plugin, sender, command, label, args.toList()).onCommand()

        return true
    }

    fun PlayerCommandContext.onCommand()
}

data class PlayerCommandContext(
    val plugin: TheBoys,
    val player: Player,
    val command: Command,
    val label: String,
    val args: List<String>
) {
    fun sendSuccess(message: (Player) -> String) = player.sendMessageFormatted { "&a${message(player)}" }

    fun sendError(message: (Player) -> String) = player.sendMessageFormatted { "&c${message(player)}" }
}
