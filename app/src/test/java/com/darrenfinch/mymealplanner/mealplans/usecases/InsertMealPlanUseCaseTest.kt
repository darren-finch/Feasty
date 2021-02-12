package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToDbMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanToMealPlan
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class InsertMealPlanUseCaseTest {
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: InsertMealPlanUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = InsertMealPlanUseCase(
            repository
        )
    }

    @Test
    internal fun `insertMealPlan() inserts meal plan into repository`() = runBlockingTest {
        val uiMealPlanToBeInserted = TestDefModels.defUiMealPlan
        val dbUiMealPlanToBeInserted = mealPlanToDbMealPlan(
            uiMealPlanToMealPlan(
                uiMealPlanToBeInserted.copy(id = Constants.EXISTING_ITEM_ID)
            )
        )

        SUT.insertMealPlan(uiMealPlanToBeInserted)

        coVerify { repository.insertMealPlan(dbUiMealPlanToBeInserted) }
    }
}