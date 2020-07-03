package com.darrenfinch.mymealplanner.domain.allmeals.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.model.MealsRepository

class AllMealsController(private val getAllMealsUseCase: GetAllMealsUseCase) : AllMealsViewMvc.Listener {
    private lateinit var viewMvc: AllMealsViewMvc

    private fun navigateToAddEditMealsFragment() {
        val directions = AllMealsFragmentDirections.actionMealsFragmentToAddEditMealFragment()
        Navigation.findNavController(viewMvc.getRootView()).navigate(directions)
    }

    fun bindView(viewMvc: AllMealsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun fetchMeals(viewLifecycleOwner: LifecycleOwner) {
        getAllMealsUseCase.fetchAllMeals().observe(viewLifecycleOwner, Observer { newMeals ->
            viewMvc.bindMeals(newMeals)
        })
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun addNewMealClicked() {
        navigateToAddEditMealsFragment()
    }
}
