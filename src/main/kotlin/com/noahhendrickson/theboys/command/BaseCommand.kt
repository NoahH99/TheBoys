package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.Transport
import com.noahhendrickson.theboys.util.formatColors
import org.bukkit.command.Command
import org.bukkit.entity.Player

data class BaseCommand(
    val plugin: TheBoys,
    val player: Player,
    val command: Command,
    val label: String,
    val args: Array<out String>
) {

    val transportationCountdownTasks: MutableList<Transport>
        get() = plugin.transportationCountdownTasks

    fun String.toPlayerOrNull() = plugin.server.getPlayer(this)

    fun sendError(message: Player.() -> String) {
        val formatted = "&c${message(player)}".formatColors()
        player.sendMessage(formatted)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseCommand) return false

        if (player != other.player) return false
        if (command != other.command) return false
        if (label != other.label) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + command.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }
}