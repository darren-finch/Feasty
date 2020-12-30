package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseFood

@Dao
interface FoodsDao
{
    @Query("SELECT * FROM foods")
    fun getFoods(): LiveData<List<DatabaseFood>>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFood(foodId: Int): DatabaseFood

    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodObservable(foodId: Int): LiveData<DatabaseFood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: DatabaseFood)

    @Update
    suspend fun updateFood(food: DatabaseFood)

    @Query("DELETE FROM foods WHERE id = :foodId")
    suspend fun deleteFood(foodId: Int)
}