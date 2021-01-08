package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_ID
import com.darrenfinch.mymealplanner.model.MainRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class DeleteFoodUseCaseTest {
    private val defaultMealWithMealFood = TestData.defaultMeal.copy(foods = listOf(TestData.defaultMealFood))
    private val defaultMeal2WithMealFood2 = TestData.defaultMeal2.copy(foods = listOf(TestData.defaultMealFood2))
    private val defaultMeal1LiveData = MutableLiveData(defaultMealWithMealFood)
    private val defaultMeal2LiveData = MutableLiveData(defaultMeal2WithMealFood2)
    private val defaultMealFoodListLiveData = TestData.defaultMealFoodListLiveData

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteFoodUseCase(repository)
    }

    @Test
    internal fun `deleteFood() passes foodId to repository`() {
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.deleteFood(DEFAULT_VALID_FOOD_ID) }
    }

    @Test
    internal fun `deleteFood() updates all meals with invalid meal foods`() {
        every { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) } returns defaultMealFoodListLiveData
        every { repository.getMeal(DEFAULT_VALID_MEAL_ID) } returns defaultMeal1LiveData
        every { repository.getMeal(DEFAULT_VALID_MEAL_ID + 1) } returns defaultMeal2LiveData
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) }
        verify { repository.updateMeal(TestData.defaultMeal) }
        verify { repository.updateMeal(TestData.defaultMeal2) }
    }

    @Test
    internal fun `deleteFood() deletes invalid meal foods`() {
        every { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) } returns defaultMealFoodListLiveData
        every { repository.getMeal(DEFAULT_VALID_MEAL_ID) } returns defaultMeal1LiveData
        every { repository.getMeal(DEFAULT_VALID_MEAL_ID + 1) } returns defaultMeal2LiveData
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.getMealFoodsFromFoodId(DEFAULT_VALID_FOOD_ID) }
        verify { repository.deleteMealFood(DEFAULT_VALID_MEAL_FOOD_ID) }
        verify { repository.deleteMealFood(DEFAULT_VALID_MEAL_FOOD_ID + 1) wasNot Called }
    }
}
