package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetAllMealsUseCaseTest {
    private val defaultMealListLiveData = TestData.defaultMealListLiveData

    private val repository = mockk<MealsRepository>()

    private lateinit var SUT: GetAllMealsUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetAllMealsUseCase(repository)
        every { repository.getMeals() } returns defaultMealListLiveData
    }

    @Test
    internal fun `fetchAllMeals() gets meals from repository`() {
        SUT.fetchAllMeals()
        verify { repository.getMeals() }
    }

    @Test
    internal fun `fetchAllMeals() returns correct data from repository`() {
        assertEquals(SUT.fetchAllMeals(), defaultMealListLiveData)
    }
}