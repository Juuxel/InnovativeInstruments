package juuxel.innovativeinstruments.gui.menu

import io.github.cottonmc.cotton.gui.CottonCraftingController
import io.github.cottonmc.cotton.gui.EmptyInventory
import net.minecraft.container.ArrayPropertyDelegate
import net.minecraft.container.BlockContext
import net.minecraft.container.PropertyDelegate
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.Inventory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

// These methods make contexts behave more like in vanilla,
// and allow me to avoid implementing BlockEntityClientSerializable for GUI purposes.

/**
 * Creates a menu context of the [world] and [pos] if the world is server-side.
 * Otherwise returns [BlockContext.EMPTY].
 */
fun blockContextOf(world: World?, pos: BlockPos): BlockContext =
    if (world == null || world.isClient) BlockContext.EMPTY
    else BlockContext.create(world, pos)

/**
 * Returns the inventory at the [context], or a [BasicInventory] with the [size] if absent.
 */
fun getBlockInventory(context: BlockContext, size: Int): Inventory {
    val value = CottonCraftingController.getBlockInventory(context)
    return if (value === EmptyInventory.INSTANCE) BasicInventory(size) else value
}

/**
 * Returns the property delegate at the [context], or a [ArrayPropertyDelegate] with the [size] if absent.
 */
fun getBlockProperties(context: BlockContext, size: Int): PropertyDelegate {
    val value = CottonCraftingController.getBlockPropertyDelegate(context)
    return if (value.size() == 0 && value is ArrayPropertyDelegate) {
        ArrayPropertyDelegate(size)
    } else {
        value
    }
}
