package com.darrenfinch.mymealplanner.screens.mealplanform

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MealPlanFormValidatorTest {
    private val mealPlanFormData = mockk<MealPlanFormData>(relaxUnitFun = true)

    private val listener1 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)
    private val listener2 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)

    private val SUT = MealPlanFormValidator()

    @BeforeEach
    internal fun setUp() {
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)
    }

    @AfterEach
    internal fun tearDown() {
        SUT.unregisterListener(listener1)
        SUT.unregisterListener(listener2)
    }

    @Test
    internal fun `testIsValidAndNotify() - title is empty - listeners notified of validation failure`() {
        val mealPlanDetails = TestDefModels.defUiMealPlan.copy(title = "")
        every { mealPlanFormData.getMealPlanDetails() } returns mealPlanDetails

        SUT.testIsValidAndNotify(mealPlanFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
            listener2.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - all values are valid - listeners notified of validation success`() {
        val mealPlanDetails = TestDefModels.defUiMealPlan.copy(title = "title")
        every { mealPlanFormData.getMealPlanDetails() } returns mealPlanDetails

        SUT.testIsValidAndNotify(mealPlanFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Success)
            listener2.onValidateForm(ValidationResult.Success)
        }
    }
}