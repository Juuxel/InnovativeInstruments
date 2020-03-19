package juuxel.innovativeinstruments.lib

import juuxel.innovativeinstruments.InnovativeInstruments
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.tag.Tag

object InnovativeTags {
    val HARMFUL: Tag<Item> = TagRegistry.item(InnovativeInstruments.id("harmful"))

    fun init() {

    }
}
