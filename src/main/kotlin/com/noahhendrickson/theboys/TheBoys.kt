package com.noahhendrickson.theboys

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.properties.Delegates

class TheBoys : JavaPlugin(), Listener {

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
        enableEvent("enable-random-drops", BlockBreakListener(logger, verbose))

        logger.info { "$name loaded in ${System.currentTimeMillis() - start}ms." }
    }

    private fun enableEvent(path: String, listener: Listener) {
        val shouldEnable = config.getBoolean(path, false)
        if (shouldEnable) server.pluginManager.registerEvents(listener, this)
    }
}
