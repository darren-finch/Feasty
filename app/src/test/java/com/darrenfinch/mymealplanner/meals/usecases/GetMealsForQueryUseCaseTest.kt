package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
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
internal class GetMealsForQueryUseCaseTest {
    private val repository = mockk<MainRepository>()
    private val getMealUseCase = mockk<GetMealUseCase>()

    private val dbMealsFromRepository =
        listOf(
            TestDefModels.defDbMeal.copy(id = 0, title = "dbMeal1"),
            TestDefModels.defDbMeal.copy(id = 1, title = "dbMeal2"),
            TestDefModels.defDbMeal.copy(id = 2, title = "dbMeal3"),
        )

    private val uiMeal1 = mealToUiMeal(dbMealToMeal(dbMealsFromRepository[0], listOf(), listOf()))
    private val uiMeal2 = mealToUiMeal(dbMealToMeal(dbMealsFromRepository[1], listOf(), listOf()))

    private lateinit var SUT: GetMealsForQueryUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetMealsForQueryUseCase(
            repository,
            getMealUseCase
        )
    }

    @Test
    internal fun `getMealsForQuery() - query is empty - returns all valid meals from repository`() =
        runBlockingTest {
            val query = ""

            coEvery { repository.getAllMeals() } returns dbMealsFromRepository
            coEvery { getMealUseCase.getMealNullable(0) } returns uiMeal1
            coEvery { getMealUseCase.getMealNullable(1) } returns uiMeal2
            coEvery { getMealUseCase.getMealNullable(2) } returns null

            val expectedResult = listOf(
                uiMeal1,
                uiMeal2
            )
            assertEquals(expectedResult, SUT.getMealsForQuery(query))
        }

    @Test
    internal fun `getMealsForQuery() - query is not empty - returns valid meals from repository`() =
        runBlockingTest {
            val query = "asd"

            coEvery { repository.getMealsForQuery(query) } returns dbMealsFromRepository
            coEvery { getMealUseCase.getMealNullable(0) } returns uiMeal1
            coEvery { getMealUseCase.getMealNullable(1) } returns uiMeal2
            coEvery { getMealUseCase.getMealNullable(2) } returns null

            val expectedResult = listOf(
                uiMeal1,
                uiMeal2
            )
            assertEquals(expectedResult, SUT.getMealsForQuery(query))
        }
}