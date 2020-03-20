package juuxel.innovativeinstruments.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties

abstract class EngineBlock(settings: Settings) : MachineBlock(settings) {
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState.with(FACING, ctx.playerLookDirection.opposite)

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING, WORKING)
    }

    companion object {
        val FACING: DirectionProperty = Properties.FACING
        val WORKING: BooleanProperty = BooleanProperty.of("working")
    }
}
