package juuxel.innovativeinstruments.block

import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.world.BlockView

class HarmfulEngineBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState.with(FACING, ctx.playerLookDirection)

    override fun createBlockEntity(view: BlockView): BlockEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING)
    }

    companion object {
        val FACING: DirectionProperty = Properties.FACING
    }
}
