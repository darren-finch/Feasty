package com.darrenfinch.mymealplanner.screens.mealform

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.validation.BaseFormValidator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult

class MealFormValidator : BaseFormValidator<MealFormData>() {
    override fun testValid(formData: MealFormData): ValidationResult {
        val mealDetails = formData.getMealDetails()
        var errorMsg = -1
        var isValid = true

        with(mealDetails) {
            if (title.isEmpty()) {
                errorMsg = R.string.please_enter_title
                isValid = false
            } else if (foods.isEmpty()) {
                errorMsg = R.string.meal_must_have_at_least_one_food
                isValid = false
            }
        }

        return if (isValid)
            ValidationResult.Success
        else
            ValidationResult.Failure(errorMsg)
    }
}