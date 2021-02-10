package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class InsertMealFoodUseCaseTest {
    private lateinit var SUT: InsertMealFoodUseCase

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    @BeforeEach
    internal fun setUp() {
        SUT = InsertMealFoodUseCase(
            repository,
        )
    }

    @Test
    internal fun `insertMealFood() inserts meal food into repository`() = runBlockingTest {
        val uiMealFoodToBeInserted = TestDefModels.defUiMealFood
        val mealId = 1
        val dbMealFoodToBeInserted = mealFoodToDbMealFood(
            uiMealFoodToMealFood(
                uiMealFoodToBeInserted.copy(
                    id = Constants.EXISTING_ITEM_ID,
                    mealId = mealId
                )
            )
        )

        SUT.insertMealFood(uiMealFoodToBeInserted, mealId)

        coVerify { repository.insertMealFood(dbMealFoodToBeInserted) }
    }
}