package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetFoodUseCaseTest {
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: GetFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetFoodUseCase(
            repository
        )
    }

    @Test
    internal fun `getFood() returns default food when repository returns null`() = runBlockingTest {
        coEvery { repository.getFood(any()) } returns null

        assertEquals(DefaultModels.defaultUiFood, SUT.getFood(Constants.INVALID_ID))
    }

    @Test
    internal fun `getFood() returns food from repository when it's not null`() = runBlockingTest {
        val dbChicken = DefaultModels.defaultDbFood.copy(title = "Chicken")
        coEvery { repository.getFood(any()) } returns dbChicken
        val uiChicken = DefaultModels.defaultUiFood.copy(title = "Chicken")

        assertEquals(uiChicken, SUT.getFood(Constants.EXISTING_ITEM_ID))
    }

    @Test
    internal fun `getFood() passes food id into repository`() = runBlockingTest {
        coEvery { repository.getFood(any()) } returns null

        SUT.getFood(Constants.EXISTING_ITEM_ID)

        coVerify { repository.getFood(Constants.EXISTING_ITEM_ID) }
    }
}