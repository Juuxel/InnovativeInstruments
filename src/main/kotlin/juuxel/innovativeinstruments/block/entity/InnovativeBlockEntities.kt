package juuxel.innovativeinstruments.block.entity

import com.google.common.collect.ImmutableSet
import juuxel.innovativeinstruments.InnovativeInstruments
import juuxel.innovativeinstruments.block.InnovativeBlocks
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry
import team.reborn.energy.Energy

object InnovativeBlockEntities {
    val INDUSTRIAL_COMPOSTER: BlockEntityType<IndustrialComposterBlockEntity> = register(
        "industrial_composter",
        ::IndustrialComposterBlockEntity,
        InnovativeBlocks.INDUSTRIAL_COMPOSTER
    )

    val HARMFUL_ENGINE: BlockEntityType<HarmfulEngineBlockEntity> = register(
        "harmful_engine",
        ::HarmfulEngineBlockEntity,
        InnovativeBlocks.HARMFUL_ENGINE
    )

    fun init() {
        // Set up energy
        Energy.registerHolder({ it is MachineBlockEntity }, { (it as MachineBlockEntity).energy })
    }

    private fun <T : BlockEntity> register(name: String, supplier: () -> T, vararg blocks: Block): BlockEntityType<T> =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            InnovativeInstruments.id(name),
            BlockEntityType(supplier, ImmutableSet.copyOf(blocks), null)
        )
}
