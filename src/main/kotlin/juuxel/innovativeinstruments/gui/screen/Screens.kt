package juuxel.innovativeinstruments.gui.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import juuxel.innovativeinstruments.gui.menu.IndustrialComposterMenu
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity

@Environment(EnvType.CLIENT)
class IndustrialComposterScreen(
    menu: IndustrialComposterMenu,
    player: PlayerEntity
) : CottonInventoryScreen<IndustrialComposterMenu>(menu, player)
