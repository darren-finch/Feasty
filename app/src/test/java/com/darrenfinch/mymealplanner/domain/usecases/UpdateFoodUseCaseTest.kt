package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UpdateFoodUseCaseTest {
    private val defaultFoodData = TestData.defaultFood

    private val repository = mockk<FoodsRepository>(relaxUnitFun = true)

    private lateinit var SUT: UpdateFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = UpdateFoodUseCase(repository)
    }

    @Test
    internal fun `updateFood() passes food to repository`() {
        SUT.updateFood(defaultFoodData)
        verify { repository.updateFood(defaultFoodData) }
    }
}