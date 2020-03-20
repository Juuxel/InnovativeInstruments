package juuxel.innovativeinstruments.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity

abstract class MachineBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL
}
