package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class UpsertMealUseCaseTest {
    private val insertMealUseCase = mockk<InsertMealUseCase>(relaxUnitFun = true)
    private val updateMealUseCase = mockk<UpdateMealUseCase>(relaxUnitFun = true)

    private lateinit var SUT: UpsertMealUseCase

    @BeforeEach
    fun setUp() {
        SUT = UpsertMealUseCase(
            insertMealUseCase,
            updateMealUseCase
        )
    }

    @Test
    internal fun `upsertMeal() inserts food when food is new`() = runBlockingTest {
        val uiMeal = TestDefModels.defUiMeal.copy(id = Constants.NEW_ITEM_ID)

        SUT.upsertMeal(uiMeal)

        coVerify { insertMealUseCase.insertMeal(uiMeal) }
    }

    @Test
    internal fun `upsertMeal() updates food when food already exists`() = runBlockingTest {
        val uiMeal = TestDefModels.defUiMeal.copy(id = Constants.EXISTING_ITEM_ID)

        SUT.upsertMeal(uiMeal)

        coVerify { updateMealUseCase.updateMeal(uiMeal) }
    }
}