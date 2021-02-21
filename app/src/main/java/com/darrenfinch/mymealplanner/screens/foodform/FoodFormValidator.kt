package com.darrenfinch.mymealplanner.screens.foodform

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult

class FoodFormValidator : BaseFormValidator<FoodFormData>() {
    override fun testValid(formData: FoodFormData): ValidationResult {
        val foodDetails = formData.getFoodDetails()
        var errorMsg = -1
        var isValid = true

        with (foodDetails) {
            if (title.isEmpty()) {
                errorMsg = R.string.please_enter_title
                isValid = false
            } else if (servingSize.quantity <= 0) {
                errorMsg = R.string.serving_size_quantity_must_be_greater_than_zero
                isValid = false
            }

            // all macros are assumed to be > 0 because of the input text fields
        }

        return if (isValid)
            ValidationResult.Success
        else
            ValidationResult.Failure(errorMsg)
    }
}