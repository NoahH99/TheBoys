package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.extensions.Permissions
import com.noahhendrickson.theboys.extensions.hasPermission
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

interface RandomDropsCommand : PlayerCommand, TabCompleter, AdminCommand {

    val table: MutableMap<Material, Material>
        get() = plugin.table

    override fun PlayerCommandContext.onCommand() {
        if (player.hasPermission(Permissions.ADMIN)) validatedCommand()
        else sendError { "You don't have permission to use this command." }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        return if (sender.hasPermission(Permissions.ADMIN)) validatedTabComplete(sender, command, alias, args.toList())
        else null
    }
}
