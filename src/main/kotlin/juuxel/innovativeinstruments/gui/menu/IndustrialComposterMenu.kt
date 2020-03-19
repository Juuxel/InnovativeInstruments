package juuxel.innovativeinstruments.gui.menu

import io.github.cottonmc.cotton.gui.CottonCraftingController
import io.github.cottonmc.cotton.gui.widget.*
import juuxel.innovativeinstruments.InnovativeInstruments.id
import net.minecraft.container.BlockContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class IndustrialComposterMenu(
    syncId: Int, playerInventory: PlayerInventory, title: Text, ctx: BlockContext
) : CottonCraftingController(null, syncId, playerInventory, getBlockInventory(ctx, 2), getBlockProperties(ctx, 6)) {
    init {
        val root = WPlainPanel()
        val u = 9

        root.add(WLabel(title, WLabel.DEFAULT_TEXT_COLOR), 0, 0)
        root.add(WBar(ENERGY_BAR_EMPTY, ENERGY_BAR_FULL, 0, 4).withTooltip("gui.innovative_instruments.energy"), 0, 2 * u, 2 * u, 6 * u)

        root.add(WItemSlot.of(blockInventory, 0), 4 * u, 4 * u)
        root.add(WItemSlot.outputOf(blockInventory, 1), 12 * u, 4 * u)
        root.add(WBar(EMPTY_PROGRESS_OVERLAY, PROGRESS_OVERLAY, 3, 5), 12 * u - 4, 4 * u - 4, 24, 24)

        root.add(WBar(PROGRESS_ARROW_BACKGROUND, PROGRESS_ARROW_FOREGROUND, 1, 2, WBar.Direction.RIGHT), 6 * u + 7, 4 * u, 32, 2 * u)

        root.add(WLabel(playerInventory.displayName, WLabel.DEFAULT_TEXT_COLOR), 0, 9 * u - 2)
        root.add(createPlayerInventoryPanel(), 0, 10 * u)

        setRootPanel(root)
        root.validate(this)
    }

    companion object {
        private val PROGRESS_OVERLAY = id("textures/gui/industrial_composter/output_progress_overlay_full.png")
        private val EMPTY_PROGRESS_OVERLAY = id("textures/gui/industrial_composter/output_progress_overlay_empty.png")
        private val PROGRESS_ARROW_BACKGROUND = id("textures/gui/industrial_composter/progress_arrow_background.png")
        private val PROGRESS_ARROW_FOREGROUND = id("textures/gui/industrial_composter/progress_arrow_foreground.png")
        private val ENERGY_BAR_EMPTY = id("textures/gui/energy_bar_empty.png")
        private val ENERGY_BAR_FULL = id("textures/gui/energy_bar_full.png")
    }
}
