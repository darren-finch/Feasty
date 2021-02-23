package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DeleteMealPlanUseCaseTest {
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteMealPlanUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteMealPlanUseCase(
            repository
        )
    }

    @Test
    internal fun `deleteMealPlan() deletes meal plan from repository`() = runBlockingTest {
        val mealPlanId = 1

        SUT.deleteMealPlan(mealPlanId)

        coVerify { repository.deleteMealPlan(mealPlanId) }
    }
}