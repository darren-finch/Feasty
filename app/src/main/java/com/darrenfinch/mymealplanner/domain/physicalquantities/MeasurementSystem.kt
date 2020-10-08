package com.darrenfinch.physicalquantities

import com.darrenfinch.physicalquantities.units.MeasurementType
import com.darrenfinch.physicalquantities.units.MeasurementUnit
import com.darrenfinch.physicalquantities.units.metric.*
import com.darrenfinch.physicalquantities.units.uscustomary.*
import com.darrenfinch.physicalquantities.units.imperial.*

enum class MeasurementSystem {
    USCustomary,
    Imperial,
    Metric;

    fun getBaseUnitFor(measurementSystem: MeasurementSystem, measurementType: MeasurementType): MeasurementUnit {
        return when (measurementType) {
            MeasurementType.Length -> {
                when(measurementSystem) {
                    USCustomary -> USInch()
                    Imperial -> ImperialInch()
                    else -> Meter()
                }
            }
            MeasurementType.Mass -> {
                when(measurementSystem) {
                    USCustomary -> USPound()
                    Imperial -> ImperialPound()
                    else -> Gram()
                }
            }
            MeasurementType.LiquidVolume -> {
                when(measurementSystem) {
                    USCustomary -> USCup()
                    Imperial -> ImperialPint()
                    else -> Liter()
                }
            }
        }
    }
}