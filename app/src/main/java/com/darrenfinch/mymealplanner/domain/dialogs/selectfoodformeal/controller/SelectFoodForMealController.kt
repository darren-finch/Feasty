package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val onDialogEventListener: BaseDialog.OnDialogEventListener
) : BaseController, SelectFoodForMealViewMvc.Listener {

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
        onDialogEventListener.onFinish(
            SelectFoodForMealDialog.TAG,
            Bundle().apply {
                putInt(FOOD_ID, food.id)
            }
        )
    }

    override fun setState(state: Bundle?) {}
    override fun getState(): Bundle {
        return Bundle()
    }
}