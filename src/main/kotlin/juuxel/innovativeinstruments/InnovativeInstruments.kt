package juuxel.innovativeinstruments

import juuxel.innovativeinstruments.block.InnovativeBlocks
import juuxel.innovativeinstruments.block.entity.InnovativeBlockEntities
import juuxel.innovativeinstruments.gui.InnovativeGuis
import juuxel.innovativeinstruments.lib.InnovativeTags
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object InnovativeInstruments {
    const val ID: String = "innovative_instruments"

    fun id(path: String) = Identifier(ID, path)

    fun init() {
        InnovativeBlocks.init()
        InnovativeBlockEntities.init()
        InnovativeGuis.init()
        InnovativeTags.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        InnovativeGuis.initClient()
    }
}
