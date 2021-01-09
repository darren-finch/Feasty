package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_ID
import com.darrenfinch.mymealplanner.model.MainRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class DeleteFoodUseCaseTest {
    private val defaultMealWithMealFood = TestData.defaultMeal.copy(foods = listOf(TestData.defaultMealFood))
    private val defaultMeal2WithMealFood2 = TestData.defaultMeal2.copy(foods = listOf(TestData.defaultMealFood2))

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteFoodUseCase(repository)
    }

    @Test
    internal fun `deleteFood() passes foodId to repository`() {
        coEvery { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) } returns TestData.defaultMealFoodList
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID) } returns defaultMealWithMealFood
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID + 1) } returns defaultMeal2WithMealFood2
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.deleteFood(DEFAULT_VALID_FOOD_ID) }
    }

    @Test
    internal fun `deleteFood() updates all meals with invalid meal foods`() {
        coEvery { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) } returns TestData.defaultMealFoodList
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID) } returns defaultMealWithMealFood
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID + 1) } returns defaultMeal2WithMealFood2
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        coVerify { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) }
        verify { repository.updateMeal(TestData.defaultMeal) }
    }

    @Test
    internal fun `deleteFood() deletes invalid meal foods`() {
        coEvery { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) } returns TestData.defaultMealFoodList
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID) } returns defaultMealWithMealFood
        coEvery { repository.getMealSuspended(DEFAULT_VALID_MEAL_ID + 1) } returns defaultMeal2WithMealFood2
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        coVerify { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) }
        verify { repository.deleteMealFood(DEFAULT_VALID_MEAL_FOOD_ID) }
    }
}
