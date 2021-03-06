package com.noahhendrickson.theboys.listener

import com.noahhendrickson.theboys.TheBoys
import com.noahhendrickson.theboys.extensions.info
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class BlockBreakListener(private val plugin: TheBoys, private val verbose: Boolean) : Listener {

    private val table: MutableMap<Material, Material>
        get() = plugin.table

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

    /**
     * Spawns a random item that can be dropped.
     */
    private fun BlockBreakEvent.spawnRandomItem() {
        val material = block.getItemToDrop()
        val itemCount = material.dropAt(block.location)

        if (itemCount == -1) {
            table.remove(block.type)
            spawnRandomItem()
            return
        }

        if (verbose) sendLogMessage(player, block.type, material, itemCount)
    }

    /**
     * Gets the associated [Material] from the [table] for the given [Block] or generates a random [Material]
     * and adds that [Material] to the [table].
     */
    private fun Block.getItemToDrop(): Material {
        return if (table.containsKey(type)) {
            table.getValue(type)
        } else {
            var material = materials.random()

            while (table.containsKey(material) || table.containsValue(material)) {
                material = materials.random()
            }

            table[type] = material
            table.getValue(type)
        }
    }

    /**
     * Drops 1-3 of given [Material] at the given [Location].
     *
     * @return the amount of items dropped or -1 if the item could not be dropped.
     */
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

    /**
     * Sends a log message to the console about a player who broke a block and what material it was replaced with.
     */
    private fun sendLogMessage(player: Player, brokenMaterial: Material, droppedMaterial: Material, itemCount: Int) {
        plugin.info {
            "Player '${player.name}' broke '${brokenMaterial.name}' and it dropped '${droppedMaterial.name}'. ($itemCount)"
        }
    }
}
