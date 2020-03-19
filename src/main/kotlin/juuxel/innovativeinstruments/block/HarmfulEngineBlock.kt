package juuxel.innovativeinstruments.block

import juuxel.innovativeinstruments.block.entity.HarmfulEngineBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.world.BlockView

class HarmfulEngineBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState.with(FACING, ctx.playerLookDirection.opposite)

    override fun createBlockEntity(view: BlockView) = HarmfulEngineBlockEntity()

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING, WORKING)
    }

    companion object {
        val FACING: DirectionProperty = Properties.FACING
        val WORKING: BooleanProperty = BooleanProperty.of("working")
    }
}
