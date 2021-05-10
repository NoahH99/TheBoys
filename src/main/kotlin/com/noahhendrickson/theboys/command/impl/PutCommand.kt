package com.noahhendrickson.theboys.command.impl

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.command.PlayerCommandContext
import com.noahhendrickson.theboys.command.RandomDropsCommand
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class PutCommand(override val plugin: TheBoys) : RandomDropsCommand {

    override fun PlayerCommandContext.validatedCommand() {
        if (args.size < 2) return sendError { "Please specify two blocks that will be added to the table." }

        val key = Material.getMaterial(args[0].toUpperCase())
        val value = Material.getMaterial(args[1].toUpperCase())

        if (key == null || value == null || !key.isBlock) return sendError { "You specified an invalid material." }

        if (table.contains(key)) {
            table.replace(key, value)
            sendSuccess { "You have replaced ${key.name} with ${value.name}." }
        } else {
            table[key] = value
            sendSuccess { "You have put ${key.name} with ${value.name}." }
        }
    }

    override fun validatedTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: List<String>
    ): List<String> {
        return Material.values()
            .map { it.name }
            .filter { it.startsWith(args[args.size - 1], ignoreCase = true) }
    }
}
