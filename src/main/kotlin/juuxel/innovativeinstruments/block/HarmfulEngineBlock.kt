package juuxel.innovativeinstruments.block

import juuxel.innovativeinstruments.block.entity.HarmfulEngineBlockEntity
import net.minecraft.world.BlockView

class HarmfulEngineBlock(settings: Settings) : EngineBlock(settings) {
    override fun createBlockEntity(view: BlockView) = HarmfulEngineBlockEntity()
}
