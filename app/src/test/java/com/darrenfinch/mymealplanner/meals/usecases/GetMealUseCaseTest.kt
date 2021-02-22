package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToUiMealFood
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetMealUseCaseTest {

    private val mealId = 1
    private val dbMealFood1 = TestDefModels.defDbMealFood.copy(id = 0, referencedFoodId = 1)
    private val dbMealFood2 = TestDefModels.defDbMealFood.copy(id = 1, referencedFoodId = 2)
    // foodId intentionally doesn't exist so that we can test whether
    // meal foods with null food references get loaded or not
    private val dbMealFood3 = TestDefModels.defDbMealFood.copy(id = 2, referencedFoodId = 4)
    private val dbMealFoods = listOf(dbMealFood1, dbMealFood2, dbMealFood3)

    private val dbFoodReference1 = TestDefModels.defDbFood.copy(id = 1)
    private val dbFoodReference2 = TestDefModels.defDbFood.copy(id = 2)

    private val dbMeal = TestDefModels.defDbMeal

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: GetMealUseCase

    @BeforeEach
    fun setUp() {
        SUT = GetMealUseCase(
            repository
        )

        coEvery { repository.getMeal(mealId) } returns dbMeal
        coEvery { repository.getFood(dbMealFood1.referencedFoodId) } returns dbFoodReference1
        coEvery { repository.getFood(dbMealFood2.referencedFoodId) } returns dbFoodReference2
        coEvery { repository.getFood(dbMealFood3.referencedFoodId) } returns null
        coEvery { repository.getMealFoodsForMeal(mealId) } returns dbMealFoods
    }

    @Test
    internal fun `getMeal() gets meal from repository with all meal foods except the ones without a food reference`() =
        runBlockingTest {
            val expectedUiMealFood1 = mealFoodToUiMealFood(
                dbMealFoodToMealFood(dbMealFood1, dbFoodReference1)
            )
            val expectedUiMealFood2 = mealFoodToUiMealFood(
                dbMealFoodToMealFood(dbMealFood2, dbFoodReference2)
            )
            val expectedUiMealFoods = listOf(expectedUiMealFood1, expectedUiMealFood2)
            val expectedUiMeal = TestDefModels.defUiMeal.copy(foods = expectedUiMealFoods)

            assertEquals(expectedUiMeal, SUT.getMeal(mealId))
        }

    @Test
    internal fun `getMeal() returns default ui meal if no db meal was found`() = runBlockingTest {
        coEvery { repository.getMeal(mealId) } returns null

        assertEquals(TestDefModels.defUiMeal, SUT.getMeal(mealId))
    }
}