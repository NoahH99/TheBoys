package com.noahhendrickson.theboys.command.impl

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.command.PlayerCommandContext
import com.noahhendrickson.theboys.command.RandomDropsCommand
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class MaterialsCommand(override val plugin: TheBoys) : RandomDropsCommand {

    override fun PlayerCommandContext.validatedCommand() {
        val material = if (args.isNotEmpty()) Material.getMaterial(args[0]) else null

        if (material == null) {
            if (args.isNotEmpty()) return sendError { "You specified an invalid material." }
            return table.forEach { (key, value) -> sendSuccess { "${key.name} &8-> &a${value.name}" } }
        }

        if (table.contains(material)) {
            table[material]!!.apply { sendSuccess { "${material.name} -> $name" } }
        } else sendError { "The ${material.name} is not in the table. Use '/put' to add it." }

    }

    override fun validatedTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: List<String>
    ): List<String> {
        return table.keys
            .map { it.name }
            .filter { it.startsWith(args[args.size - 1], ignoreCase = true) }
    }
}