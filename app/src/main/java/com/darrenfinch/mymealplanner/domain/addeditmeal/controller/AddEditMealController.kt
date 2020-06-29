package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import androidx.fragment.app.FragmentManager
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealDialog

class AddEditMealController(private val fragmentManager: FragmentManager) : AddEditMealViewMvc.Listener {
    private lateinit var viewMvc: AddEditMealViewMvc

    fun bindView(viewMvc: AddEditMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun addNewFoodClicked() {
        val selectFoodForMealDialog = SelectFoodForMealDialog()
        selectFoodForMealDialog.show(fragmentManager, "SelectFoodForMealDialog")
    }
}