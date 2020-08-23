package com.darrenfinch.mymealplanner.model.data.entitysubdata

import java.util.*

enum class MetricUnit {
    GRAM {
        override fun toString() = metricUnitsToStringValues[GRAM] ?: ""
    },
    LITER {
        override fun toString() = metricUnitsToStringValues[LITER] ?: ""
    },
    UNIT {
        override fun toString() = metricUnitsToStringValues[UNIT] ?: ""
    };

    companion object {
        val defaultUnit =
            GRAM

        //Make sure these maps are in the exact order needed and have the exact values needed.
        val metricUnitsToStringValues = mapOf(
            GRAM to "g",
            LITER to "l",
            UNIT to "unit"
        )
        val stringValuesToMetricUnits = mapOf(
            "g" to GRAM,
            "l" to LITER,
            "unit" to UNIT
        )

        fun fromString(value: String): MetricUnit {
            return stringValuesToMetricUnits[formatString(
                value
            )] ?: defaultUnit
        }

        private fun formatString(value: String): String {
            return value.toLowerCase(Locale.ROOT).removeSuffix("s")
        }
    }
}