package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityDialog.Companion.MEAL_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.NEW_MEAL_FOOD
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator

class SelectMealFoodQuantityController(
    private val getFoodUseCase: GetFoodUseCase,
    private val onDialogEventListener: BaseDialog.OnDialogEventListener
) : BaseController, SelectMealFoodQuantityViewMvc.Listener {

    private var foodId = -1
    private var mealId = -1
    private lateinit var viewMvc: SelectMealFoodQuantityViewMvc

    fun bindView(viewMvc: SelectMealFoodQuantityViewMvc) {
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

    override fun onFoodServingSizeChosen(foodBeforeUpdatingMacros: Food, selectedFoodQuantity: PhysicalQuantity) {
        foodBeforeUpdatingMacros.let {
            val updatedMealFood = MacroCalculator.updateMacrosForMealFoodWithNewServingSize(
                MealFood(
                    0,
                    it.id,
                    mealId,
                    it.title,
                    it.servingSize,
                    it.macroNutrients
                ),
                selectedFoodQuantity
            )
            onDialogEventListener.onFinish(SelectMealFoodQuantityDialog.TAG, Bundle().apply { putSerializable(NEW_MEAL_FOOD, updatedMealFood) })
        }
    }

    override fun setState(state: Bundle?) {
        foodId = state?.getInt(FOOD_ID) ?: -1
        mealId = state?.getInt(MEAL_ID) ?: -1
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(FOOD_ID, foodId)
            putInt(MEAL_ID, mealId)
        }
    }
}
