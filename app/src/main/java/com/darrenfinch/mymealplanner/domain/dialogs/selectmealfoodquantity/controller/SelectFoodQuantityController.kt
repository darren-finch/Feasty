package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class SelectFoodQuantityController(
    private val getFoodUseCase: GetFoodUseCase,
    private val onDialogEventListener: BaseDialog.OnDialogEventListener
) : BaseController, SelectFoodQuantityViewMvc.Listener {

    private var foodId = -1
    private lateinit var viewMvc: SelectFoodQuantityViewMvc

    fun bindView(viewMvc: SelectFoodQuantityViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchFood(viewLifecycleOwner: LifecycleOwner) {
        getFoodUseCase.fetchFood(foodId).observe(viewLifecycleOwner, Observer {
            viewMvc.bindFood(it)
        })
    }

    override fun onFoodServingSizeChosen(selectedFood: Food, selectedFoodQuantity: PhysicalQuantity) {
        onDialogEventListener.onFinish(
            SelectFoodQuantityDialog.TAG,
            Bundle().apply {
                putSerializable(SELECTED_FOOD, selectedFood)
                putSerializable(SELECTED_FOOD_QUANTITY, selectedFoodQuantity)
            }
        )
    }

    override fun setState(state: Bundle?) {
        foodId = state?.getInt(FOOD_ID) ?: -1
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(FOOD_ID, foodId)
        }
    }
}
