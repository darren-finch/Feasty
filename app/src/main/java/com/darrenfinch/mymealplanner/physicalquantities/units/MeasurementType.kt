package com.darrenfinch.mymealplanner.physicalquantities.units

enum class MeasurementType {
    Length,
    Mass,
    Volume;

    companion object {
        val defaultType = Mass

        val stringsToValidMeasurementTypes: HashMap<String, MeasurementType> = hashMapOf("Mass" to Mass, "Volume" to Volume)
        val validMeasurementTypesToStrings: HashMap<MeasurementType, String> = hashMapOf(Mass to "Mass", Volume to "Volume")
    }

    fun isCompatibleForConversionTo(otherMeasurementType: MeasurementType): Boolean {
        return this == otherMeasurementType
    }
}
