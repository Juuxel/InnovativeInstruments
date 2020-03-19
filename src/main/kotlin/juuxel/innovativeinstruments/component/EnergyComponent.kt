package juuxel.innovativeinstruments.component

import net.minecraft.nbt.CompoundTag
import team.reborn.energy.EnergySide
import team.reborn.energy.EnergyStorage
import team.reborn.energy.EnergyTier

/**
 * A mutable [energy storage][EnergyStorage] component.
 *
 * @property energy the current stored energy
 * @property maximum the maximum stored energy
 * @property tier the [energy tier][EnergyTier]
*/
data class EnergyComponent(
    var energy: Double,
    val maximum: Double,
    @get:JvmName("_getTier") val tier: EnergyTier = EnergyTier.MEDIUM
): EnergyStorage {
    /**
     * Constructs an empty energy component.
     */
    constructor(maximum: Double, tier: EnergyTier = EnergyTier.MEDIUM) : this(0.0, maximum, tier)

    override fun getStored(face: EnergySide) = energy

    override fun setStored(amount: Double) {
        this.energy = amount
    }

    override fun getMaxStoredPower() = maximum

    override fun getTier() = tier

    /**
     * Loads this component from the data at the [key] in the [tag].
     */
    fun fromTag(key: String, tag: CompoundTag) {
        energy = tag.getDouble(key)
    }

    /**
     * Saves this component to the [key] in the [tag].
     */
    fun toTag(key: String, tag: CompoundTag) {
        tag.putDouble(key, energy)
    }
}
