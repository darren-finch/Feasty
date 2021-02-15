package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DeleteFoodUseCaseTest {
    private val foodId = 1
    private val invalidMealFood1 = TestDefModels.defDbMealFood.copy(id = 0)
    private val invalidMealFood2 = TestDefModels.defDbMealFood.copy(id = 1)
    private val invalidMealFoods = listOf(invalidMealFood1, invalidMealFood2)

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteFoodUseCase(
            repository
        )

        coEvery { repository.getMealFoodsForFood(foodId) } returns invalidMealFoods
    }

    @Test
    internal fun `deleteFood() deletes all invalid meal foods`() = runBlockingTest {
        SUT.deleteFood(foodId)

        coVerify {
            repository.deleteMealFood(0)
            repository.deleteMealFood(1)
        }
    }

    @Test
    internal fun `deleteFood() deletes food from repository`() = runBlockingTest {
        SUT.deleteFood(foodId)

        coVerify {
            repository.deleteFood(foodId)
        }
    }
}