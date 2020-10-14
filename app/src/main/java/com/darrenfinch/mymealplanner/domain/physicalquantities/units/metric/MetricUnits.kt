package com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric

import com.darrenfinch.mymealplanner.domain.physicalquantities.MeasurementSystem
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.MeasurementType

abstract class MetricUnit(val prefix: MetricPrefix) : MeasurementUnit() {
    /**
     * The base unit ratio is determined by the prefix multiplier of this unit.
     */
    override fun getBaseUnitRatio(): Double {
        return prefix.getMultiplier()
    }

    override fun getMeasurementSystem(): MeasurementSystem {
        return MeasurementSystem.Metric
    }

    override fun equals(other: Any?): Boolean {
        return if(other !is MetricUnit) {
            false
        }
        else {
            return super.equals(other) && prefix == other.prefix
        }
    }

    override fun hashCode(): Int {
        return prefix.hashCode()
    }
}

class Meter(prefix: MetricPrefix = NullPrefix()) : MetricUnit(prefix) {

    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return "${prefix}meter${if (plural) "s" else ""}"
    }
}

class Gram(prefix: MetricPrefix = NullPrefix()) : MetricUnit(prefix) {

    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return "${prefix}gram${if (plural) "s" else ""}"
    }
}

class Liter(prefix: MetricPrefix = NullPrefix()) : MetricUnit(prefix) {

    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return "${prefix}liter${if (plural) "s" else ""}"
    }
}
