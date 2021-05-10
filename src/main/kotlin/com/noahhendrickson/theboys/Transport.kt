package com.noahhendrickson.theboys

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

data class Transport(
    private val transporter: Player,
    private val destination: Player,
    private val task: BukkitTask
) {
    val transporterUUID = transporter.uniqueId
    val transporterName = transporter.name

    val destinationUUID = destination.uniqueId
    val destinationName = destination.name

    fun cancel() = task.cancel()
}
