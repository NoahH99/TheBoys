package com.noahhendrickson.theboys.extensions

import org.bukkit.command.CommandSender

fun CommandSender.sendMessageFormatted(message: (CommandSender) -> String) = sendMessage(message(this).formatColors())
