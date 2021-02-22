package com.darrenfinch.mymealplanner.data.room.models.meals

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "meals", indices = [Index("id")])
data class DbMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)
