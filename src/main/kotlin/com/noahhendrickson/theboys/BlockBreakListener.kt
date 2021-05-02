package com.noahhendrickson.theboys

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.util.logging.Logger
import kotlin.random.Random

class BlockBreakListener(private val logger: Logger, private val verbose: Boolean) : Listener {

    private val table = HashMap<Material, Material>().withDefault { Material.BARRIER }
    private val materials by lazy {
        Material.values()
            .filter { !it.isAir }
            .filter { it.isItem || it.isSolid || it.isEdible }
            .filter { !it.name.endsWith("SPAWN_EGG") }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isDropItems = false
        event.spawnRandomItem()
    }

    private fun BlockBreakEvent.spawnRandomItem() {
        val material = block.getItemToDrop()
        val itemCount = material.dropAt(block.location)

        if (itemCount == -1) {
            table.remove(block.type)
            spawnRandomItem()
            return
        }

        if (verbose) {
            sendLogMessage(player, block.type, material, itemCount)
        }
    }

    private fun Block.getItemToDrop(): Material {
        return if (table.containsKey(type)) {
            table.getValue(type)
        } else {
            table[type] = materials.random()
            table.getValue(type)
        }
    }

    private fun Material.dropAt(location: Location): Int {
        val item = ItemStack(this)
        val itemCount = Random.nextInt(3) + 1

        try {
            repeat(itemCount) { location.world.dropItemNaturally(location, item) }
        } catch (e: IllegalArgumentException) {
            return -1
        }

        return itemCount
    }

    private fun sendLogMessage(player: Player, brokenMaterial: Material, droppedMaterial: Material, itemCount: Int) {
        logger.info {
            "Player '${player.name}' broke '${brokenMaterial.name}' and it dropped '${droppedMaterial.name}'. ($itemCount)"
        }
    }
}