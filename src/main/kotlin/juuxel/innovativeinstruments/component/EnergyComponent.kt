package juuxel.innovativeinstruments.component

import net.minecraft.nbt.CompoundTag
import team.reborn.energy.EnergySide
import team.reborn.energy.EnergyStorage
import team.reborn.energy.EnergyTier

data class EnergyComponent(var energy: Double, val maximum: Double): EnergyStorage {
    constructor(maximum: Double) : this(0.0, maximum)

    override fun getStored(face: EnergySide) = energy

    override fun setStored(amount: Double) {
        this.energy = amount
    }

    override fun getMaxStoredPower() = maximum

    override fun getTier() = EnergyTier.MEDIUM

    fun fromTag(key: String, tag: CompoundTag) {
        energy = tag.getDouble(key)
    }

    fun toTag(key: String, tag: CompoundTag) {
        tag.putDouble(key, energy)
    }
}
