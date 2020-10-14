package com.darrenfinch.mymealplanner.model.data.entitysubdata

import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.imperial.ImperialOunce
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric.Gram
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

internal class MacroNutrientsTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------

    //endregion Helper Fields ----------------------------------------------------------------------

    //region Set up / Tear down

    //endregion Set up / Tear down

    //region Tests ---------------------------------------------------------------------------------
    @Test
    internal fun convertFoodServingSizes() {
        val unitServingSize = PhysicalQuantity(200.0, Gram())
        val desiredServingSize = PhysicalQuantity(17.637, ImperialOunce())
        val convertedDesiredServingSize = desiredServingSize.convert(Gram())

        val initialCalories = 170
        val caloriesPerGram = unitServingSize.quantity / initialCalories
        val finalCalories = convertedDesiredServingSize.quantity / caloriesPerGram

        assertEquals(425.17, finalCalories, 0.5)
    }
    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}