package com.darrenfinch.mymealplanner.screens.foodform

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Gram
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FoodFormValidatorTest {
    private val foodFormData = mockk<FoodFormData>()

    private val listener1 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)
    private val listener2 = mockk<BaseFormValidator.Listener>(relaxUnitFun = true)

    private val SUT = FoodFormValidator()

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
        val foodDetails = TestDefModels.defUiFood.copy(title = "")
        every { foodFormData.getFoodDetails() } returns foodDetails

        SUT.testIsValidAndNotify(foodFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
            listener2.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - other values valid but serving size quantity less than 0 - listeners notified of validation failure`() {
        val foodDetails = TestDefModels.defUiFood.copy(title = "title", servingSize = PhysicalQuantity(-1.0, MeasurementUnit.defaultUnit))
        every { foodFormData.getFoodDetails() } returns foodDetails

        SUT.testIsValidAndNotify(foodFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.serving_size_quantity_must_be_greater_than_zero))
            listener2.onValidateForm(ValidationResult.Failure(R.string.serving_size_quantity_must_be_greater_than_zero))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - other values valid but serving size quantity equal to 0 - listeners notified of validation failure`() {
        val foodDetails = TestDefModels.defUiFood.copy(title = "title", servingSize = PhysicalQuantity(0.0, MeasurementUnit.defaultUnit))
        every { foodFormData.getFoodDetails() } returns foodDetails

        SUT.testIsValidAndNotify(foodFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Failure(R.string.serving_size_quantity_must_be_greater_than_zero))
            listener2.onValidateForm(ValidationResult.Failure(R.string.serving_size_quantity_must_be_greater_than_zero))
        }
    }

    @Test
    internal fun `testIsValidAndNotify() - all values are valid - listeners notified of validation success`() {
        val foodDetails = TestDefModels.defUiFood.copy(title = "Chicken", servingSize = PhysicalQuantity(113.0, Gram()))
        every { foodFormData.getFoodDetails() } returns foodDetails

        SUT.testIsValidAndNotify(foodFormData)

        verify {
            listener1.onValidateForm(ValidationResult.Success)
            listener2.onValidateForm(ValidationResult.Success)
        }
    }
}