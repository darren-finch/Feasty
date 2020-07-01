package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AddEditFoodViewModelFactory(private val foodId: Int, private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddEditFoodViewModel(foodId, application) as T
    }
}
