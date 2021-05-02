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

        val verbose = config.getBoolean("verbose", true)

        server.pluginManager.registerEvents(BlockBreakListener(logger, verbose), this)

        logger.info { "$name loaded in ${System.currentTimeMillis() - start}ms." }
    }
}
