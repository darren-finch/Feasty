package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FoodFormViewModelFactory(private val foodId: Int, private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodFormViewModel(foodId, application) as T
    }
}
