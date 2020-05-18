package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodsDao
{
    @Query("SELECT * FROM foods")
    fun getFoods() : LiveData<Food>

    @Query("SELECT * FROM foods WHERE id IN (:ids)")
    suspend fun getFoodsWithIds(ids: List<Int>) : List<Food>

    @Insert
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)
}