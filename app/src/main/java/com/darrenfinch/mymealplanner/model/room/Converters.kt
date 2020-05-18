package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter

class Converters
{
    @TypeConverter
    fun listOfIdsToString(value: List<Int>) : String = value.joinToString(",")
    @TypeConverter
    fun stringToListOfIds(value: String) : List<Int>
    {
        return if(value.isNotEmpty()) value.split(",").map { it.toInt() } else mutableListOf()
    }
    @TypeConverter
    fun measurementUnitToString(value: MeasurementUnit) = value.name
    @TypeConverter
    fun stringToMeasurementUnit(value: String) = enumValueOf<MeasurementUnit>(value)
}