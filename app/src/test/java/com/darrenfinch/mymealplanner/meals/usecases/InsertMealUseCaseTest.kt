package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class InsertMealUseCaseTest {
    private lateinit var SUT: InsertMealUseCase

    private val uiMealToBeInserted = TestDefModels.defUiMeal.copy(title = "defUiMeal")
    private val dbMealToBeInserted = mealToDbMeal(uiMealToMeal(uiMealToBeInserted))
    private val idOfInsertedMeal = 0L

    private val repository = mockk<MainRepository>(relaxUnitFun = true)
    private val insertMealFoodUseCase = mockk<InsertMealFoodUseCase>(relaxUnitFun = true)

    @BeforeEach
    internal fun setUp() {
        SUT = InsertMealUseCase(
            repository,
            insertMealFoodUseCase
        )

        coEvery { repository.insertMeal(any()) } returns idOfInsertedMeal
    }

    @Test
    internal fun `insertMeal() inserts meal into repository`() = runBlockingTest {
        SUT.insertMeal(uiMealToBeInserted)

        coVerify { repository.insertMeal(dbMealToBeInserted) }
    }

    @Test
    internal fun `insertMeal() inserts new meal foods with id of inserted meal after inserting meal `() =
        runBlockingTest {
            val uiMealFoodToBeInserted1 = TestDefModels.defUiMealFood.copy(
                id = Constants.NEW_ITEM_ID,
                title = "uiMealFoodToBeInserted1"
            )
            val uiMealFoodToBeInserted2 = TestDefModels.defUiMealFood.copy(
                id = Constants.NEW_ITEM_ID,
                title = "uiMealFoodToBeInserted2"
            )
            val uiMealToBeInserted2 = uiMealToBeInserted.copy(
                foods = listOf(
                    uiMealFoodToBeInserted1,
                    uiMealFoodToBeInserted2
                )
            )
            val dbMealToBeInserted2 = mealToDbMeal(uiMealToMeal(uiMealToBeInserted2))

            SUT.insertMeal(uiMealToBeInserted2)

            coVerifyOrder {
                repository.insertMeal(dbMealToBeInserted2)
                insertMealFoodUseCase.insertMealFood(uiMealFoodToBeInserted1, idOfInsertedMeal.toInt())
                insertMealFoodUseCase.insertMealFood(uiMealFoodToBeInserted2, idOfInsertedMeal.toInt())
            }
        }
}