package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class SelectFoodQuantityController(
    private val getFoodUseCase: GetFoodUseCase,
    private val dialogsManager: DialogsManager
) : BaseController, SelectFoodQuantityViewMvc.Listener {

    data class SavedState(val food: Food) : BaseController.BaseSavedState

    private var foodIdArg = -1

    private var foodState = DefaultModels.defaultFood

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
        viewMvc.bindFood(foodState)

        if(foodState == DefaultModels.defaultFood) {
            getFoodUseCase.fetchFood(foodIdArg).observe(viewLifecycleOwner, Observer {
                foodState = it
                viewMvc.bindFood(it)
            })
        }
    }

    fun setArgs(foodId: Int) {
        this.foodIdArg = foodId
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            foodState = state.food
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(foodState)
    }

    override fun onFoodServingSizeChosen(selectedFood: Food, selectedFoodQuantity: PhysicalQuantity) {
        dialogsManager.clearDialog()
    }
}
