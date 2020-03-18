package juuxel.innovativeinstruments.block

import juuxel.innovativeinstruments.block.entity.IndustrialCompostBlockEntity
import juuxel.innovativeinstruments.gui.InnovativeGuis
import juuxel.innovativeinstruments.gui.openFabricContainer
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class IndustrialCompostBlock(settings: Settings) : BlockWithEntity(settings) {
    init {
        defaultState = defaultState.with(WORKING, false)
    }

    // TODO: Sounds?

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState.with(FACING, ctx.playerFacing.opposite)
    }

    override fun onUse(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult
    ): ActionResult {
        player.openFabricContainer(InnovativeGuis.INDUSTRIAL_COMPOST, pos)
        return ActionResult.SUCCESS
    }

    override fun createBlockEntity(world: BlockView): BlockEntity = IndustrialCompostBlockEntity()

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(FACING, WORKING)
    }

    companion object {
        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
        val WORKING: BooleanProperty = BooleanProperty.of("working")
    }
}
