package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.Constants
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DeleteFoodUseCaseTest {

    private val repository = mockk<FoodsRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteFoodUseCase(repository)
    }

    @Test
    internal fun `deleteFood() passes foodId to repository`() {
        SUT.deleteFood(Constants.DEFAULT_FOOD_ID)
        verify { repository.deleteFood(Constants.DEFAULT_FOOD_ID) }
    }
}
