package juuxel.innovativeinstruments.gui

import juuxel.innovativeinstruments.InnovativeInstruments
import juuxel.innovativeinstruments.gui.screen.IndustrialComposterScreen
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.client.gui.screen.ingame.ContainerScreen
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

object InnovativeGuis {
    val INDUSTRIAL_COMPOSTER: Identifier = InnovativeInstruments.id("industrial_composter")

    fun init() {
        registerMenu(INDUSTRIAL_COMPOSTER)
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        registerScreen(INDUSTRIAL_COMPOSTER, ::IndustrialComposterScreen)
    }

    private fun registerMenu(
        id: Identifier
    ) = ContainerProviderRegistry.INSTANCE.registerFactory(id) { syncId, _, player, buf ->
        val world = player.world
        val pos = buf.readBlockPos()
        val provider = world.getBlockState(pos).createContainerFactory(world, pos)
        provider?.createMenu(syncId, player.inventory, player)
    }

    private inline fun <reified C : Container> registerScreen(
        id: Identifier,
        crossinline screenFn: (C, PlayerEntity) -> ContainerScreen<in C>
    ) = ScreenProviderRegistry.INSTANCE.registerFactory(id) { syncId, _, player, buf ->
        val world = player.world
        val pos = buf.readBlockPos()
        val provider = world.getBlockState(pos).createContainerFactory(world, pos)
        provider?.let {
            screenFn(it.createMenu(syncId, player.inventory, player) as C, player)
        }
    }
}

fun PlayerEntity.openFabricContainer(id: Identifier, pos: BlockPos) {
    if (!world.isClient) {
        ContainerProviderRegistry.INSTANCE.openContainer(id, this) { it.writeBlockPos(pos) }
    }
}
