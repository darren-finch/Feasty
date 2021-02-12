package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToDbMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class InsertMealPlanMealUseCaseTest {
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: InsertMealPlanMealUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = InsertMealPlanMealUseCase(
            repository
        )
    }

    @Test
    internal fun `insertMealPlanMeal() inserts meal plan meal into repository`() = runBlockingTest {
        val uiMealPlanMealToBeInserted = TestDefModels.defUiMealPlanMeal
        val dbMealPlanMealToBeInserted = mealPlanMealToDbMealPlanMeal(
            uiMealPlanMealToMealPlanMeal(
                uiMealPlanMealToBeInserted.copy(
                    id = Constants.EXISTING_ITEM_ID,
                )
            )
        )

        SUT.insertMealPlanMeal(uiMealPlanMealToBeInserted)

        coVerify { repository.insertMealPlanMeal(dbMealPlanMealToBeInserted) }
    }
}