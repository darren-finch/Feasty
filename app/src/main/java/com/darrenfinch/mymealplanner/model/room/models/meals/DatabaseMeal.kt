package com.darrenfinch.mymealplanner.model.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* This class is what is stored in Room.
* It's then converted to a Meal class by the repository, which is displayed in the front-end.
* This class is here to accomplish 2 things:
* 1. Ability to fetch foods from their own table by id, separate from meals table
* 2. No type converters needed for meals
*/
@Entity(tableName = "meals")
data class DatabaseMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)
