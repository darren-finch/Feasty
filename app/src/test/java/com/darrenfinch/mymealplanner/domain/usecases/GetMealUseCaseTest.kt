package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_ID
import com.darrenfinch.mymealplanner.TestData.defaultMealLiveData
import com.darrenfinch.mymealplanner.model.MealsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetMealUseCaseTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val repository = mockk<MealsRepository>(relaxUnitFun = true)

    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: GetMealUseCase

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = GetMealUseCase(repository)
    }
    //endregion Set up / Tear down

    //region Tests ---------------------------------------------------------------------------------
    @Test
    internal fun `getMeal() returns correct meal from repository`() {
        every { repository.getMeal(any()) } returns defaultMealLiveData
        val returnedLiveData = SUT.getMeal(DEFAULT_VALID_MEAL_ID)
        assert(returnedLiveData == defaultMealLiveData)
        verify { repository.getMeal(DEFAULT_VALID_MEAL_ID) }
    }
    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}