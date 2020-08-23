package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.model.MainRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InsertFoodUseCaseTest {

    private val defaultFoodData = TestData.defaultFood

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: InsertFoodUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = InsertFoodUseCase(repository)
    }

    @Test
    internal fun `insertFood() passes food to repository`() {
        SUT.insertFood(defaultFoodData)
        verify { repository.insertFood(defaultFoodData) }
    }
}