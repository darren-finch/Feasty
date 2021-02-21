package com.darrenfinch.mymealplanner.screens.mealplanform

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult

class MealPlanFormValidator : BaseFormValidator<MealPlanFormData>() {
    override fun testValid(formData: MealPlanFormData): ValidationResult {
        val mealDetails = formData.getMealPlanDetails()
        var errorMsg = -1
        var isValid = true

        with(mealDetails) {
            if (title.isEmpty()) {
                errorMsg = R.string.please_enter_title
                isValid = false
            }
        }

        return if (isValid)
            ValidationResult.Success
        else
            ValidationResult.Failure(errorMsg)
    }
}