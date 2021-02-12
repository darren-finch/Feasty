package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class UpdateMealUseCaseTest {
    private lateinit var SUT: UpdateMealUseCase

    private val insertMealFoodUseCase = mockk<InsertMealFoodUseCase>(relaxUnitFun = true)
    private val updateMealFoodUseCase = mockk<UpdateMealFoodUseCase>(relaxUnitFun = true)
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private val existingUiMealFood = TestDefModels.defUiMealFood.copy(id = 1)
    private val newUiMealFood = TestDefModels.defUiMealFood.copy(id = Constants.NEW_ITEM_ID)
    private val newUiMealFoods = listOf(existingUiMealFood, newUiMealFood)

    private val existingDbMealFood1 = mealFoodToDbMealFood(uiMealFoodToMealFood(existingUiMealFood)).copy(id = 1)
    private val existingDbMealFood2 = mealFoodToDbMealFood(uiMealFoodToMealFood(existingUiMealFood)).copy(id = 2)
    private val oldDbMealFoods = listOf(existingDbMealFood1, existingDbMealFood2)

    private val mealId = 1
    private val uiMealToBeUpdated = TestDefModels.defUiMeal.copy(id = mealId, foods = newUiMealFoods)

    private val dbMealToBeUpdated = mealToDbMeal(uiMealToMeal(uiMealToBeUpdated))

    @BeforeEach
    internal fun setUp() {
        SUT = UpdateMealUseCase(
            repository,
            insertMealFoodUseCase,
            updateMealFoodUseCase
        )

        coEvery { repository.getMealFoodsForMeal(any()) } returns oldDbMealFoods
    }

    @Test
    internal fun `updateMeal() inserts new meal foods into repository`() = runBlockingTest {
        SUT.updateMeal(uiMealToBeUpdated)

        coVerify { insertMealFoodUseCase.insertMealFood(newUiMealFood, mealId) }
    }

    @Test
    internal fun `updateMeal() updates existing meal foods in repository`() = runBlockingTest {
        SUT.updateMeal(uiMealToBeUpdated)

        coVerify { updateMealFoodUseCase.updateMealFood(existingUiMealFood) }
    }

    @Test
    internal fun `updateMeal() deletes existing meal foods from repository that aren't in new meal foods list`() = runBlockingTest {
        SUT.updateMeal(uiMealToBeUpdated)

        coVerify { repository.deleteMealFood(2) }
    }

    @Test
    internal fun `updateMeal() updates meal in repository`() = runBlockingTest {
        SUT.updateMeal(uiMealToBeUpdated)

        coVerify { repository.updateMeal(dbMealToBeUpdated) }
    }

    @Test
    internal fun `updateMeal() does everything in correct order`() = runBlockingTest {
        SUT.updateMeal(uiMealToBeUpdated)

        coVerifyOrder {
            insertMealFoodUseCase.insertMealFood(any(), any())
            updateMealFoodUseCase.updateMealFood(any())
            repository.deleteMealFood(any())
            repository.updateMeal(any())
        }
    }
}