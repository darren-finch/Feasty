package com.darrenfinch.physicalquantities.units

import com.darrenfinch.physicalquantities.MeasurementSystem

abstract class MeasurementUnit {
    /**
     * Returns the type of measurement this unit represents, e.g length, volume, mass, etc.
     */
    abstract fun getMeasurementType(): MeasurementType

    /**
     * Returns the measurement system this unit belongs to, e.g metric, imperial, or US customary.
     */
    abstract fun getMeasurementSystem(): MeasurementSystem

    /**
     * For a given measurement system and measurement type, this ratio represents how many base units you need to equal 1 of this unit.
     * A base unit is any unit that has a base unit ratio of 1.0.
     * E.g for a length measurement type in the imperial measurement system, the base unit is an ImperialInch.
     * Every other unit of the same measurement type in the same measurement system, has a ratio back to that base unit.
     * Therefore, it makes sense that an ImperialFoot has a base unit ratio of 12.0 because you need 12 inches to make 1 foot.
     *
     * Note that the metric system works slightly differently than the imperial or US customary system.
     * In the metric system, the base unit ratio is determined by prefixes instead of some arbitrary multiplier.
     * The base unit of each metric measurement type is just the unit without a prefix, e.g meter, gram, and liter.
     */
    abstract fun getBaseUnitRatio(): Double

    /**
     * @param plural    Whether the unit is plural (e.g inches is the plural form of inch). Pass true if the quantity related to this unit is greater than one.
     * @param abbreviated   Whether to return a shortened representation of the unit, e.g "in" instead of "inches"
     * @return The correct string representation of this unit, accounting for the noun number.
     */
    abstract fun getUnitAsString(plural: Boolean = false, abbreviated: Boolean = false): String

    override fun equals(other: Any?): Boolean {
        return if(other !is MeasurementUnit) {
            false
        }
        else {
            //There is no state, so we're just comparing functions.
            getMeasurementType() == other.getMeasurementType() &&
                    getBaseUnitRatio() == other.getBaseUnitRatio()
        }
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return getUnitAsString()
    }
}