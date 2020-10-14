package com.darrenfinch.mymealplanner.domain.physicalquantities

import com.darrenfinch.mymealplanner.domain.physicalquantities.units.MeasurementType
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.imperial.ImperialInch
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.imperial.ImperialPint
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.imperial.ImperialPound
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric.Gram
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric.Liter
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric.Meter
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.uscustomary.USCup
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.uscustomary.USInch
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.uscustomary.USPound

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