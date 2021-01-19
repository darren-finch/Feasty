package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val dialogsManager: DialogsManager
) : BaseController, SelectFoodForMealViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchAllFoods(viewLifecycleOwner: LifecycleOwner) {
        getAllFoodsUseCase.fetchAllFoods().observe(viewLifecycleOwner, Observer {
            viewMvc.bindFoods(it)
        })
    }

    override fun onFoodChosen(food: Food) {
        dialogsManager.clearDialog()
    }

    override fun restoreState(state: BaseController.BaseSavedState) {}
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}