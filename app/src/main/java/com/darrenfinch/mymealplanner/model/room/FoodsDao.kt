package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.data.Food

@Dao
interface FoodsDao
{
    @Query("SELECT * FROM foods")
    fun getFoods() : LiveData<List<Food>>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFood(foodId: Int) : Food

    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodObservable(foodId: Int) : LiveData<Food>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Query("DELETE FROM foods WHERE id = :foodId")
    suspend fun deleteFood(foodId: Int)
}