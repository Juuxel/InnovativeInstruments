package juuxel.innovativeinstruments.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import juuxel.innovativeinstruments.component.EnergyComponent
import juuxel.innovativeinstruments.gui.menu.IndustrialComposterMenu
import juuxel.innovativeinstruments.lib.NbtKeys
import net.minecraft.container.BlockContext
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.CompoundTag
import kotlin.math.roundToInt

class IndustrialComposterBlockEntity : MachineBlockEntity(
    InnovativeBlockEntities.INDUSTRIAL_COMPOSTER
), PropertyDelegateHolder {
    override val energy = EnergyComponent(MAX_ENERGY)
    private var stage: Stage = Stage.IDLE
    private var inputProgress: Int = 0
    private var outputProgress: Int = 0
    private var biomass: Int = 0

    /*
        0 = energy
        1 = input progress
        2 = max input progress
        3 = biomass level
        4 = max energy
        5 = max biomass
    */
    private val properties: PropertyDelegate = object : PropertyDelegate {
        override fun get(index: Int): Int = when (index) {
            0 -> energy.energy.roundToInt()
            1 -> inputProgress
            2 -> MAX_INPUT_PROGRESS
            3 -> biomass
            4 -> MAX_ENERGY.toInt()
            5 -> MAX_BIOMASS
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun set(index: Int, value: Int): Unit = when (index) {
            0 -> { energy.energy = value.toDouble() }
            1 -> { inputProgress = value }
            3 -> { biomass = value }
            2, 4, 5 -> {} // Max values
            else -> throw IllegalArgumentException("Unknown property key: $index")
        }

        override fun size() = 4
    }

    override fun getPropertyDelegate() = properties

    override fun createContainer(syncId: Int, playerInventory: PlayerInventory) =
        IndustrialComposterMenu(syncId, playerInventory, displayName, BlockContext.create(world, pos))

    override fun getInvSize(): Int = 2

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        stage = Stage.values()[tag.getByte(NbtKeys.STAGE).toInt()]
        inputProgress = tag.getInt(NbtKeys.INPUT_PROGRESS)
        outputProgress = tag.getInt(NbtKeys.OUTPUT_PROGRESS)
        biomass = tag.getInt(NbtKeys.BIOMASS)
    }

    override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
        putByte(NbtKeys.STAGE, stage.ordinal.toByte())
        putInt(NbtKeys.INPUT_PROGRESS, inputProgress)
        putInt(NbtKeys.OUTPUT_PROGRESS, outputProgress)
        putInt(NbtKeys.BIOMASS, biomass)
    }

    private enum class Stage {
        IDLE,
        PROCESSING_INPUT,
        PROCESSING_OUTPUT
    }

    companion object {
        private const val MAX_BIOMASS: Int = 4
        private const val MAX_INPUT_PROGRESS: Int = 100
        private const val MAX_ENERGY: Double = 10_000.0
    }
}
