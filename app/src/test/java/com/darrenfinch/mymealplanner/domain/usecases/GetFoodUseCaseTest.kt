package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.TestData.defaultFoodLiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetFoodUseCaseTest {

    private val repository = mockk<MainRepository>()

    private lateinit var SUT: GetFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetFoodUseCase(repository)
        every { repository.getFood(any()) } returns defaultFoodLiveData
    }

    @Test
    internal fun `fetchSingleFood() passes foodId to repository`() {
        SUT.fetchFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.getFood(DEFAULT_VALID_FOOD_ID) }
    }

    @Test
    internal fun `fetchSingleFood() returns correct data from repository`() {
        assertEquals(SUT.fetchFood(DEFAULT_VALID_FOOD_ID), defaultFoodLiveData)
    }
}