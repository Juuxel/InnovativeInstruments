package juuxel.innovativeinstruments.block.entity

import juuxel.innovativeinstruments.component.EnergyComponent
import juuxel.innovativeinstruments.lib.NbtKeys
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.CompoundTag

class IndustrialComposterBlockEntity : MachineBlockEntity(InnovativeBlockEntities.INDUSTRIAL_COMPOSTER) {
    override val energy = EnergyComponent(MAX_ENERGY)
    private var stage: Stage = Stage.IDLE
    private var inputProgress: Int = 0
    private var outputProgress: Int = 0
    private var biomass: Int = 0

    override fun createContainer(syncId: Int, playerInventory: PlayerInventory): Container {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        private const val MAX_BIOMASS: Int = 7
        private const val MAX_ENERGY: Double = 10_000.0
    }
}
