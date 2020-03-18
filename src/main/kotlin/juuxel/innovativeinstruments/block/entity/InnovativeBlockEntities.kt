package juuxel.innovativeinstruments.block.entity

import com.google.common.collect.ImmutableSet
import juuxel.innovativeinstruments.InnovativeInstruments
import juuxel.innovativeinstruments.block.InnovativeBlocks
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object InnovativeBlockEntities {
    val INDUSTRIAL_COMPOST: BlockEntityType<IndustrialCompostBlockEntity> = register(
        "industrial_compost",
        ::IndustrialCompostBlockEntity,
        InnovativeBlocks.INDUSTRIAL_COMPOST
    )

    fun init() {

    }

    private fun <T : BlockEntity> register(name: String, supplier: () -> T, vararg blocks: Block): BlockEntityType<T> =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            InnovativeInstruments.id(name),
            BlockEntityType(supplier, ImmutableSet.copyOf(blocks), null)
        )
}
