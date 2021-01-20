package com.darrenfinch.mymealplanner.physicalquantities

import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementType
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.physicalquantities.units.imperial.ImperialInch
import com.darrenfinch.mymealplanner.physicalquantities.units.imperial.ImperialPint
import com.darrenfinch.mymealplanner.physicalquantities.units.imperial.ImperialPound
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Gram
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Liter
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Meter
import com.darrenfinch.mymealplanner.physicalquantities.units.uscustomary.USCup
import com.darrenfinch.mymealplanner.physicalquantities.units.uscustomary.USInch
import com.darrenfinch.mymealplanner.physicalquantities.units.uscustomary.USPound

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
            MeasurementType.Volume -> {
                when(measurementSystem) {
                    USCustomary -> USCup()
                    Imperial -> ImperialPint()
                    else -> Liter()
                }
            }
        }
    }
}