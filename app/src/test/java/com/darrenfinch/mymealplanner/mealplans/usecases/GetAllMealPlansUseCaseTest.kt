package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.mealplans.models.mappers.dbMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToUiMealPlan
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealToMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToUiMeal
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetAllMealPlansUseCaseTest {
    private val repository = mockk<MainRepository>()

    private lateinit var SUT: GetAllMealPlansUseCase

    @BeforeEach
    fun setUp() {
        SUT = GetAllMealPlansUseCase(
            repository
        )
    }

    @Test
    internal fun `getAllMealPlans() returns all meal plans from repository as presentation models`() = runBlockingTest {
        val dbMealPlans = listOf(TestDefModels.defDbMealPlan.copy(title = "defDbMealPlan1"), TestDefModels.defDbMealPlan.copy(title = "defDbMealPlan2"))
        val uiMealPlans = dbMealPlans.map { mealPlanToUiMealPlan(dbMealPlanToMealPlan(it)) }
        coEvery { repository.getAllMealPlans() } returns dbMealPlans

        assertEquals(SUT.getAllMealPlans(), uiMealPlans)
    }
}