package com.noahhendrickson.theboys.extensions

import org.bukkit.ChatColor

fun String.formatColors(altColorChar: Char = '&') = ChatColor.translateAlternateColorCodes(altColorChar, this)
