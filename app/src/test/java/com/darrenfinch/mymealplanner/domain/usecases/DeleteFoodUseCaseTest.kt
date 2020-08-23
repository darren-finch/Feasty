package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.model.MainRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DeleteFoodUseCaseTest {

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteFoodUseCase(repository)
    }

    @Test
    internal fun `deleteFood() passes foodId to repository`() {
        SUT.deleteFood(DEFAULT_VALID_FOOD_ID)
        verify { repository.deleteFood(DEFAULT_VALID_FOOD_ID) }
    }
}
