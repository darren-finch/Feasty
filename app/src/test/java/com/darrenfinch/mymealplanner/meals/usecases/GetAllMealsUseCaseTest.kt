package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetAllMealsUseCaseTest {
    private val repository = mockk<MainRepository>()
    private val getMealUseCase = mockk<GetMealUseCase>()

    private lateinit var SUT: GetAllMealsUseCase

    @BeforeEach
    fun setUp() {
        SUT = GetAllMealsUseCase(
            repository,
            getMealUseCase
        )
    }

    @Test
    internal fun `getAllMeals() gets all valid meals from repository`() = runBlockingTest {
        val dbMeal1 = TestDefModels.defDbMeal.copy(id = 0)
        val dbMeal2 = TestDefModels.defDbMeal.copy(id = 1)
        val dbMeal3 = TestDefModels.defDbMeal.copy(id = 2)
        val allDbMeals = listOf(dbMeal1, dbMeal2, dbMeal3)

        val uiMeal1 = TestDefModels.defUiMeal.copy(id = 0)
        val uiMeal2 = TestDefModels.defUiMeal.copy(id = 1)
        coEvery { repository.getAllMeals() } returns allDbMeals
        coEvery { getMealUseCase.getMealNullable(0) } returns uiMeal1
        coEvery { getMealUseCase.getMealNullable(1) } returns uiMeal2
        coEvery { getMealUseCase.getMealNullable(2) } returns null

        val expectedResult = listOf(uiMeal1, uiMeal2)
        assertEquals(expectedResult, SUT.getAllMeals())
    }
}