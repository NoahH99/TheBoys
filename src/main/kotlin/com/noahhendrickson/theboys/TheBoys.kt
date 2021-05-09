package com.noahhendrickson.theboys

import com.noahhendrickson.theboys.command.CancelCommand
import com.noahhendrickson.theboys.command.TransportationCommand
import com.noahhendrickson.theboys.listener.BlockBreakListener
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.properties.Delegates

class TheBoys : JavaPlugin() {

    val transportationCountdownTasks: MutableList<Transport> = mutableListOf()

    private var start: Long by Delegates.notNull()

    override fun onLoad() {
        super.onLoad()
        start = System.currentTimeMillis()
    }

    override fun onEnable() {
        super.onEnable()

        saveDefaultConfig()
        reloadConfig()

        val enabled = config.getBoolean("enabled", true)
        if (!enabled) super.setEnabled(false)

        val verbose = config.getBoolean("verbose", true)
        if (enableEvent("enable-random-drops", BlockBreakListener(logger, verbose))) {
            server.getPluginCommand("transport")?.setExecutor(TransportationCommand(this))
            server.getPluginCommand("cancel")?.setExecutor(CancelCommand(this))
        }

        logger.info { "$name loaded in ${System.currentTimeMillis() - start}ms." }
    }

    private fun enableEvent(path: String, listener: Listener): Boolean {
        val shouldEnable = config.getBoolean(path, false)
        if (shouldEnable) server.pluginManager.registerEvents(listener, this)
        return shouldEnable
    }
}