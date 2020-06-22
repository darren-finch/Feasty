package com.darrenfinch.mymealplanner.model.room

import java.util.*

enum class MeasurementUnit {
    GRAM {
        override fun toString() = measurementUnitsToStringValues[GRAM] ?: ""
    },
    OUNCE {
        override fun toString() = measurementUnitsToStringValues[OUNCE] ?: ""
    },
    CUP {
        override fun toString() = measurementUnitsToStringValues[CUP] ?: ""
    },
    MILLILITER {
        override fun toString() = measurementUnitsToStringValues[MILLILITER] ?: ""
    },
    UNIT {
        override fun toString() = measurementUnitsToStringValues[UNIT] ?: ""
    };

    companion object {
        val defaultUnit = GRAM

        //Make sure these maps are in the exact order needed and have the exact values needed.
        val measurementUnitsToStringValues = mapOf(
            GRAM to "g",
            OUNCE to "oz",
            CUP to "c",
            MILLILITER to "mL",
            UNIT to "unit"
        )
        val stringValuesToMeasurementUnits = mapOf(
            "g" to GRAM,
            "oz" to OUNCE,
            "c" to CUP,
            "ml" to MILLILITER,
            "unit" to UNIT
        )

        fun fromString(value: String): MeasurementUnit {
            return stringValuesToMeasurementUnits[formatString(value)] ?: defaultUnit
        }

        private fun formatString(value: String) : String {
            return value.toLowerCase(Locale.ROOT).removeSuffix("s")
        }
    }
}