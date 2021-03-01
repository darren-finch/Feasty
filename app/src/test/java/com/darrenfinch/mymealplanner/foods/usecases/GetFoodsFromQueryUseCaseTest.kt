package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetFoodsFromQueryUseCaseTest {
    private val dbFoods = listOf(
        TestDefModels.defDbFood.copy(title = "defDbFood1"),
        TestDefModels.defDbFood.copy(title = "defDbFood2")
    )
    private val uiFoods = dbFoods.map { foodToUiFood(dbFoodToFood(it)) }

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private val SUT = GetFoodsFromQueryUseCase(repository)

    @Test
    internal fun `getFoodsFromQuery() - query is empty - return all foods from repository`() =
        runBlockingTest {
            coEvery { repository.getAllFoods() } returns dbFoods

            assertEquals(uiFoods, SUT.getFoodsFromQuery(""))
            coVerify { repository.getAllFoods() }
        }

    @Test
    internal fun `getFoodsFromQuery() - query is not empty - return foods from query in repository`() =
        runBlockingTest {
            val query = "asd"
            coEvery { repository.getFoodsFromQuery(query) } returns dbFoods

            assertEquals(uiFoods, SUT.getFoodsFromQuery(query))
            coVerify { repository.getFoodsFromQuery(query) }
        }
}