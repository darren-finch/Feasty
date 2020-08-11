package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.defaultFoodLiveData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetFoodUseCaseTest {

    private val repository = mockk<FoodsRepository>()

    private lateinit var SUT: GetFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetFoodUseCase(repository)
        every { repository.fetchFood(any()) } returns defaultFoodLiveData
    }

    @Test
    internal fun `fetchSingleFood() passes foodId to repository`() {
        SUT.fetchFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.fetchFood(DEFAULT_VALID_FOOD_ID) }
    }

    @Test
    internal fun `fetchSingleFood() returns correct data from repository`() {
        assertEquals(SUT.fetchFood(DEFAULT_VALID_FOOD_ID), defaultFoodLiveData)
    }
}