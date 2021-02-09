package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetAllFoodsUseCaseTest {

    private val repository = mockk<MainRepository>()

    private lateinit var SUT: GetAllFoodsUseCase

    @BeforeEach
    fun setUp() {
        SUT = GetAllFoodsUseCase(
            repository
        )
    }

    @Test
    internal fun `getAllFoods() returns all foods from repository as presentation models`() = runBlockingTest {
        val dbFoods = listOf(TestDefModels.defDbFood.copy(title = "defDbFood1"), TestDefModels.defDbFood.copy(title = "defDbFood2"))
        val uiFoods = dbFoods.map { foodToUiFood(dbFoodToFood(it)) }
        coEvery { repository.getAllFoods() } returns dbFoods

        assertEquals(SUT.getAllFoods(), uiFoods)
    }
}