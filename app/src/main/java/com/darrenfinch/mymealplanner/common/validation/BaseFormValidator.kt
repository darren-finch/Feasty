package com.darrenfinch.mymealplanner.common.validation

abstract class BaseFormValidator<FormDataType> {
    interface Listener {
        fun onValidateForm(validationResult: ValidationResult)
    }

    private val listeners = mutableListOf<Listener>()

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun testIsValidAndNotify(formData: FormDataType) {
        val validationResult = testValid(formData)
        for (listener in listeners) {
            listener.onValidateForm(validationResult)
        }
    }

    protected abstract fun testValid(formData: FormDataType): ValidationResult
}