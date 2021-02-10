package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
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
internal class UpdateMealFoodUseCaseTest {
    private lateinit var SUT: UpdateMealFoodUseCase

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    @BeforeEach
    internal fun setUp() {
        SUT = UpdateMealFoodUseCase(
            repository
        )
    }

    @Test
    internal fun `updateMealFood() updates meal food in repository`() = runBlockingTest {
        val uiMealFoodToBeUpdated = TestDefModels.defUiMealFood
        val dbMealFoodToBeUpdated = mealFoodToDbMealFood(uiMealFoodToMealFood(uiMealFoodToBeUpdated))

        SUT.updateMealFood(uiMealFoodToBeUpdated)

        coVerify { repository.updateMealFood(dbMealFoodToBeUpdated) }
    }
}