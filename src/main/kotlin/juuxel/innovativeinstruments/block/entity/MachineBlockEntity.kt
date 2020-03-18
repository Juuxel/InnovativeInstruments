package juuxel.innovativeinstruments.block.entity

import juuxel.innovativeinstruments.component.EnergyComponent
import juuxel.innovativeinstruments.lib.NbtKeys
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.DefaultedList

abstract class MachineBlockEntity(type: BlockEntityType<*>) : LootableContainerBlockEntity(type) {
    abstract val energy: EnergyComponent
    private var items: DefaultedList<ItemStack> = DefaultedList.ofSize(invSize, ItemStack.EMPTY)

    override fun getInvStackList() = items

    override fun setInvStackList(list: DefaultedList<ItemStack>) {
        items = list
    }

    override fun getContainerName(): Text = TranslatableText(cachedState.block.translationKey)

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        energy.fromTag(NbtKeys.ENERGY, tag)
        Inventories.fromTag(tag, items)
    }

    override fun toTag(tag: CompoundTag): CompoundTag = super.toTag(tag).apply {
        energy.toTag(NbtKeys.ENERGY, tag)
        Inventories.toTag(tag, items)
    }
}
