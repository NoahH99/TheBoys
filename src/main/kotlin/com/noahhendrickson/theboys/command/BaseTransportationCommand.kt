package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.Transport
import com.noahhendrickson.theboys.util.formatColors
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

abstract class BaseTransportationCommand(private val plugin: TheBoys) : CommandExecutor {

    fun UUID.toPlayerOrNull() = plugin.server.getPlayer(this)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("&cOnly players can execute this command.".formatColors())
            return true
        }

        BaseCommand(plugin, sender, command, label, args).onCommand()

        return true
    }

    abstract fun BaseCommand.onCommand()
}