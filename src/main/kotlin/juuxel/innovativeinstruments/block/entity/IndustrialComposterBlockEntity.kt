package juuxel.innovativeinstruments.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import juuxel.innovativeinstruments.block.IndustrialComposterBlock
import juuxel.innovativeinstruments.component.EnergyComponent
import juuxel.innovativeinstruments.gui.menu.IndustrialComposterMenu
import juuxel.innovativeinstruments.gui.menu.blockContextOf
import juuxel.innovativeinstruments.lib.NbtKeys
import net.minecraft.block.ComposterBlock
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Tickable
import net.minecraft.util.math.Direction
import kotlin.math.roundToInt

class IndustrialComposterBlockEntity : MachineBlockEntity(
    InnovativeBlockEntities.INDUSTRIAL_COMPOSTER
), PropertyDelegateHolder, SidedInventory, Tickable {
    override val energy = EnergyComponent(MAX_ENERGY)
    private var stage: Stage = Stage.IDLE
    private var progress: Int = 0
    private var biomass: Int = 0

    /*
        0 = energy
        1 = progress
        2 = max progress
        3 = biomass level
        4 = max energy
        5 = max biomass
    */
    private val properties: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> energy.energy.roundToInt()
            1 -> progress
            2 -> MAX_PROGRESS
            3 -> biomass
            4 -> MAX_ENERGY.toInt()
            5 -> MAX_BIOMASS_FOR_GUI
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun set(index: Int, value: Int): Unit = when (index) {
            0 -> { energy.energy = value.toDouble() }
            1 -> { progress = value }
            3 -> { biomass = value }
            2, 4, 5 -> {} // Max values
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun size() = 6
    }

    override fun tick() {
        if (world!!.isClient) return

        val input = getInvStack(0)
        val hasInput = !input.isEmpty

        if (energy <= 0.0) {
            if (progress != 0) {
                stopProcessing()
            }
            return
        }

        if (progress == 0) {
            if (hasInput) {
                startProcessing()
            }
        } else if (progress == MAX_PROGRESS && hasInput) {
            val chance = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.getFloat(input.item)
            input.decrement(1)
            if (world!!.random.nextFloat() < chance) {
                biomass++
            }

            stopProcessing()
        } else {
            if (!hasInput) {
                stopProcessing()
            } else {
                progress++
                energy -= 2.0
                markDirty()
            }
        }

        if (biomass == MAX_BIOMASS) {
            val output = getInvStack(1)
            if (output.count == output.maxCount) return

            if (output.isEmpty) {
                setInvStack(1, ItemStack(Items.BONE_MEAL))
            } else {
                output.increment(1)
            }

            biomass = 0
        }
    }

    private fun startProcessing() {
        progress = 1
        world!!.setBlockState(pos, cachedState.with(IndustrialComposterBlock.WORKING, true))
        markDirty()
    }

    private fun stopProcessing() {
        progress = 0
        world!!.setBlockState(pos, cachedState.with(IndustrialComposterBlock.WORKING, false))
        markDirty()
    }

    override fun getPropertyDelegate() = properties

    override fun createContainer(syncId: Int, playerInventory: PlayerInventory) =
        IndustrialComposterMenu(syncId, playerInventory, displayName, blockContextOf(world, pos))

    override fun getInvSize(): Int = 2

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        stage = Stage.values()[tag.getByte(NbtKeys.STAGE).toInt()]
        progress = tag.getInt(NbtKeys.PROGRESS)
        biomass = tag.getInt(NbtKeys.BIOMASS)
    }

    override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
        putByte(NbtKeys.STAGE, stage.ordinal.toByte())
        putInt(NbtKeys.PROGRESS, progress)
        putInt(NbtKeys.BIOMASS, biomass)
    }

    override fun getInvAvailableSlots(side: Direction): IntArray =
        if (side == Direction.DOWN) OUTPUT_SLOT else INPUT_SLOT

    override fun canExtractInvStack(slot: Int, stack: ItemStack, side: Direction) = true

    override fun canInsertInvStack(slot: Int, stack: ItemStack, side: Direction?) = isValidInvStack(slot, stack)

    // More like isValidInputStack or similar
    override fun isValidInvStack(slot: Int, stack: ItemStack) =
        slot == 0 && stack.item in ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE

    private enum class Stage {
        IDLE,
        PROCESSING,
    }

    companion object {
        private const val MAX_BIOMASS: Int = 4
        private const val MAX_BIOMASS_FOR_GUI: Int = MAX_BIOMASS - 1
        private const val MAX_PROGRESS: Int = 120
        private const val MAX_ENERGY: Double = 10_000.0

        private val INPUT_SLOT = intArrayOf(0)
        private val OUTPUT_SLOT = intArrayOf(1)
    }
}
