package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.extensions.Permissions
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

interface AdminCommand : PermissibleCommand {

    override val permissions: Permissions
        get() = Permissions.ADMIN

    fun PlayerCommandContext.validatedCommand()

    fun validatedTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: List<String>
    ): List<String>?
}
