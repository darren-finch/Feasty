package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToDbFood
import com.darrenfinch.mymealplanner.foods.models.mappers.uiFoodToFood
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class UpsertFoodUseCaseTest {

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: UpsertFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = UpsertFoodUseCase(
            repository
        )
    }

    @Test
    internal fun `upsertFood() inserts food when food is new`() = runBlockingTest {
        val uiFood = TestDefModels.defUiFood.copy(id = Constants.NEW_ITEM_ID)
        val dbFood = foodToDbFood(uiFoodToFood(uiFood.copy(id = Constants.EXISTING_ITEM_ID)))

        SUT.upsertFood(uiFood)

        coVerify { repository.insertFood(dbFood) }
    }

    @Test
    internal fun `upsertFood() updates food when food already exists`() = runBlockingTest {
        val uiFood = TestDefModels.defUiFood.copy(id = Constants.EXISTING_ITEM_ID)
        val dbFood = foodToDbFood(uiFoodToFood(uiFood))

        SUT.upsertFood(uiFood)

        coVerify { repository.updateFood(dbFood) }
    }
}