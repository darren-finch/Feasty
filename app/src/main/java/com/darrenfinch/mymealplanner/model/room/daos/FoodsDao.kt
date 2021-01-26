package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood

@Dao
interface FoodsDao {
    @Query("SELECT * FROM foods")
    suspend fun getAllFoods(): List<DatabaseFood>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getFood(id: Int): DatabaseFood?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: DatabaseFood)

    @Update
    suspend fun updateFood(food: DatabaseFood)

    @Query("DELETE FROM foods WHERE id = :id")
    suspend fun deleteFood(id: Int)
}