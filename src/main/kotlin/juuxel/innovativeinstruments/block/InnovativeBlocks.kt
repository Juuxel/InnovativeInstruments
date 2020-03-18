package juuxel.innovativeinstruments.block

import juuxel.innovativeinstruments.InnovativeInstruments
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry

object InnovativeBlocks {
    val INDUSTRIAL_COMPOSTER: Block = register(
        "industrial_composter",
        IndustrialComposterBlock(createMachineSettings())
    )

    fun init() {
    }

    private fun createMachineSettings(): Block.Settings = Block.Settings.of(Material.METAL).strength(3.5F, 3.5F)

    private fun register(name: String, block: Block): Block {
        val id = InnovativeInstruments.id(name)
        Registry.register(Registry.BLOCK, id, block)
        Registry.register(Registry.ITEM, id, BlockItem(block, Item.Settings().group(ItemGroup.DECORATIONS)))
        return block
    }
}
