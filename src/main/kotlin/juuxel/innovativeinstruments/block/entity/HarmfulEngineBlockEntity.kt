package juuxel.innovativeinstruments.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import juuxel.innovativeinstruments.block.HarmfulEngineBlock
import juuxel.innovativeinstruments.component.EnergyComponent
import juuxel.innovativeinstruments.lib.InnovativeTags
import juuxel.innovativeinstruments.lib.NbtKeys
import net.minecraft.container.Container
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Tickable
import net.minecraft.util.math.Direction
import team.reborn.energy.Energy
import team.reborn.energy.EnergyTier
import kotlin.math.roundToInt

class HarmfulEngineBlockEntity : MachineBlockEntity(
    InnovativeBlockEntities.HARMFUL_ENGINE
), PropertyDelegateHolder, SidedInventory, Tickable {
    override val energy = EnergyComponent(MAX_ENERGY, EnergyTier.LOW)
    private val emptySlots = intArrayOf()
    private val slots = intArrayOf(0)
    private var progress = 0

    private val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> energy.energy.roundToInt()
            1 -> MAX_ENERGY.toInt()
            2 -> progress
            3 -> MAX_PROGRESS
            else -> throw IllegalArgumentException("Unknown property ID: $index")
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> energy.energy = value.toDouble()
                2 -> progress = value
            }
        }

        override fun size() = 4
    }

    override fun tick() {
        if (world!!.isClient) return

        if (progress > 0) {
            progress--

            val target = pos.offset(cachedState[HarmfulEngineBlock.FACING])
            val targetBe = world!!.getBlockEntity(target)
            if (Energy.valid(targetBe)) {
                val targetEnergy = Energy.of(target)
                val inserted = targetEnergy.insert(1.0)
                val selfEnergy = Energy.of(this)
                selfEnergy.insert(1.0 - inserted)
            }

            markDirty()
        } else {
            val stack = getInvStack(0)
            if (!stack.isEmpty) {
                stack.decrement(1)
                progress = MAX_PROGRESS
            }
        }
    }

    override fun createContainer(syncId: Int, playerInventory: PlayerInventory): Container {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPropertyDelegate() = propertyDelegate

    override fun getInvSize() = 1

    override fun getInvAvailableSlots(side: Direction?) =
        if (canInteractThrough(side)) slots else emptySlots

    private fun canInteractThrough(side: Direction?): Boolean =
        side == cachedState[HarmfulEngineBlock.FACING].opposite

    override fun canExtractInvStack(slot: Int, stack: ItemStack, side: Direction?): Boolean =
        canInteractThrough(side)

    override fun canInsertInvStack(slot: Int, stack: ItemStack, side: Direction?): Boolean =
        canInteractThrough(side)

    override fun isValidInvStack(slot: Int, stack: ItemStack) = stack.item.isIn(InnovativeTags.HARMFUL)

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        progress = tag.getInt(NbtKeys.PROGRESS)
    }

    override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
        tag.putInt(NbtKeys.PROGRESS, progress)
    }

    companion object {
        private const val MAX_ENERGY: Double = 4_000.0
        private const val MAX_PROGRESS: Int = 500
    }
}
