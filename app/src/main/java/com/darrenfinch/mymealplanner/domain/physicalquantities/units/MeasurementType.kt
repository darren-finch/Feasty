package com.darrenfinch.physicalquantities.units

enum class MeasurementType {
    Length,
    Mass,
    LiquidVolume;

    fun isCompatibleForConversionTo(otherMeasurementType: MeasurementType): Boolean {
        return this == otherMeasurementType
    }
}
