package com.noahhendrickson.theboys

import com.noahhendrickson.theboys.command.impl.CancelCommand
import com.noahhendrickson.theboys.command.impl.MaterialsCommand
import com.noahhendrickson.theboys.command.impl.PutCommand
import com.noahhendrickson.theboys.command.impl.TransportCommand
import com.noahhendrickson.theboys.extensions.info
import com.noahhendrickson.theboys.listener.BlockBreakListener
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.properties.Delegates

class TheBoys : JavaPlugin() {

    val transportationCountdownTasks: MutableList<Transport> = mutableListOf()
    val table = HashMap<Material, Material>().withDefault { Material.BARRIER }

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
        if (enableEvent("enable-random-drops", BlockBreakListener(this, verbose))) {
            server.getPluginCommand("materials")?.setExecutor(MaterialsCommand(this))
            server.getPluginCommand("put")?.setExecutor(PutCommand(this))

        }

        val shouldEnable = config.getBoolean("transportation", false)
        if (shouldEnable) {
            server.getPluginCommand("cancel")?.setExecutor(CancelCommand(this))
            server.getPluginCommand("transport")?.setExecutor(TransportCommand(this))
        }

        info { "$name loaded in ${System.currentTimeMillis() - start}ms." }
    }

    private fun enableEvent(path: String, listener: Listener): Boolean {
        val shouldEnable = config.getBoolean(path, false)
        if (shouldEnable) server.pluginManager.registerEvents(listener, this)
        return shouldEnable
    }
}
