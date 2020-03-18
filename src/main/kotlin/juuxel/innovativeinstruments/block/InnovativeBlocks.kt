package juuxel.innovativeinstruments.block

import juuxel.innovativeinstruments.InnovativeInstruments
import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object InnovativeBlocks {
    val INDUSTRIAL_COMPOSTER: Block = register(
        "industrial_composter",
        IndustrialComposterBlock(createMachineSettings())
    )

    fun init() {
    }

    private fun createMachineSettings(): Block.Settings = TODO()

    private fun register(name: String, block: Block) = Registry.register(
        Registry.BLOCK, InnovativeInstruments.id(name), block
    )
}
