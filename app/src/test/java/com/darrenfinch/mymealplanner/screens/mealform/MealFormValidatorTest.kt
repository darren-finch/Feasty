package com.darrenfinch.mymealplanner.screens.mealform

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

internal class MealFormValidatorTest {
    private val mealFormData = mockk<MealFormData>(relaxUnitFun = true)

    private val listener1 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)
    private val listener2 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)

    private val SUT = MealFormValidator()

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
        val mealDetails = TestDefModels.defUiMeal.copy(title = "")
        every { mealFormData.getMealDetails() } returns mealDetails

        SUT.testIsValidAndNotify(mealFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
            listener2.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - other values are valid but foods is empty - listeners notified of validation failure`() {
        val mealDetails = TestDefModels.defUiMeal.copy(title = "title", foods = emptyList())
        every { mealFormData.getMealDetails() } returns mealDetails

        SUT.testIsValidAndNotify(mealFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.meal_must_have_at_least_one_food))
            listener2.onValidateForm(ValidationResult.Failure(R.string.meal_must_have_at_least_one_food))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - all values are valid - listeners notified of validation success`() {
        val mealDetails = TestDefModels.defUiMeal.copy(title = "title", foods = listOf(TestDefModels.defUiMealFood))
        every { mealFormData.getMealDetails() } returns mealDetails

        SUT.testIsValidAndNotify(mealFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Success)
            listener2.onValidateForm(ValidationResult.Success)
        }
    }
}