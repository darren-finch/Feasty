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

internal class GetAllFoodsUseCaseTest {
    private val defaultFoodListLiveData = TestData.defaultFoodListLiveData

    private val repository = mockk<FoodsRepository>()

    private lateinit var SUT: GetAllFoodsUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetAllFoodsUseCase(repository)
        every { repository.getFoods() } returns defaultFoodListLiveData
    }

    @Test
    internal fun `fetchAllFoods() gets foods from repository`() {
        SUT.fetchAllFoods()
        verify { repository.getFoods() }
    }

    @Test
    internal fun `fetchAllFoods() returns correct data from repository`() {
        assertEquals(SUT.fetchAllFoods(), defaultFoodListLiveData)
    }
}