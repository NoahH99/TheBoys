package com.noahhendrickson.theboys.extensions

import org.bukkit.plugin.Plugin

fun Plugin.info(message: () -> String) = logger.info(message())
