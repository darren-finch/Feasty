package com.darrenfinch.mymealplanner.meals.usecases

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
internal class DeleteMealUseCaseTest {
    private val mealId = 1
    private val invalidMealFood1 = TestDefModels.defDbMealFood.copy(id = 0)
    private val invalidMealFood2 = TestDefModels.defDbMealFood.copy(id = 1)
    private val invalidMealFoods = listOf(invalidMealFood1, invalidMealFood2)

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteMealUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteMealUseCase(
            repository
        )

        coEvery { repository.getMealFoodsForMeal(mealId) } returns invalidMealFoods
    }

    @Test
    internal fun `deleteMeal() deletes all invalid meal foods`() = runBlockingTest {
        SUT.deleteMeal(mealId)

        coVerify {
            repository.deleteMealFood(0)
            repository.deleteMealFood(1)
        }
    }

    @Test
    internal fun `deleteMeal() deletes meal from repository`() = runBlockingTest {
        SUT.deleteMeal(mealId)

        coVerify {
            repository.deleteMeal(mealId)
        }
    }
}