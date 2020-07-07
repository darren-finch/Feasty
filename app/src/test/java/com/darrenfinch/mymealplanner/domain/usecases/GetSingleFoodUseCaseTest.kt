package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.Constants
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetSingleFoodUseCaseTest {

    private val defaultFoodData = TestData.defaultFoodData
    private val defaultFoodLiveData = MutableLiveData(defaultFoodData)

    private val repository = mockk<FoodsRepository>()

    private lateinit var SUT: GetSingleFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetSingleFoodUseCase(repository)
        every { repository.fetchFood(any()) } returns defaultFoodLiveData
    }

    @Test
    internal fun `fetchSingleFood() passes foodId to repository`() {
        SUT.fetchSingleFood(Constants.DEFAULT_FOOD_ID)
        verify { repository.fetchFood(Constants.DEFAULT_FOOD_ID) }
    }

    @Test
    internal fun `fetchSingleFood() returns correct data from repository`() {
        assertEquals(SUT.fetchSingleFood(Constants.DEFAULT_FOOD_ID), defaultFoodLiveData)
    }
}