package com.darrenfinch.mymealplanner.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AllMealsViewModelFactory(private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app)
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return AllMealsViewModel(app) as T
    }
}