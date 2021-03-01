package com.darrenfinch.mymealplanner.data.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodsDao {
    @Query("SELECT * FROM foods")
    suspend fun getAllFoods(): List<DbFood>

    @Query("SELECT * FROM foods WHERE title LIKE :query")
    suspend fun getFoodsFromQuery(query: String): List<DbFood>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getFood(id: Int): DbFood?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: DbFood)

    @Update
    suspend fun updateFood(food: DbFood)

    @Query("DELETE FROM foods WHERE id = :id")
    suspend fun deleteFood(id: Int)
}