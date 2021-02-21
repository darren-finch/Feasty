package com.darrenfinch.mymealplanner.common.validation

import androidx.annotation.StringRes

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(@StringRes val errorMsg: Int) : ValidationResult()
}