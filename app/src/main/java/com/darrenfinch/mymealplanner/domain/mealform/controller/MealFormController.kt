package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.CURRENT_MEAL
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.MEAL_ID
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFormController(
    private val insertMealUseCase: InsertMealUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager
) : BaseController, MealFormViewMvc.Listener, DialogsManager.OnDialogEventListener {

    private var mealId = Constants.INVALID_ID
    private lateinit var currentMeal: Meal

    private lateinit var viewMvc: MealFormViewMvc

    private val isEditingExistingMeal: Boolean
        get() = mealId != Constants.INVALID_ID

    fun bindView(viewMvc: MealFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        dialogsManager.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsManager.unregisterListener(this)
    }

    fun onViewCreated(viewLifecycleOwner: LifecycleOwner) {
        if (isEditingExistingMeal) { // TODO: Can't fetch meal if current meal is dirty (has been edited)
            getMealUseCase.getMeal(mealId).observe(viewLifecycleOwner, Observer {
                currentMeal = it
            })
        } else {
            currentMeal = DefaultModels.defaultMeal
            viewMvc.bindMealDetails(currentMeal)
        }
    }

    override fun addNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun doneButtonClicked() {
        screensNavigator.goBack()
        if(isEditingExistingMeal) {
            updateMealUseCase.updateMeal(currentMeal)
        }
        else {
            insertMealUseCase.insertMeal(currentMeal)
        }
    }

    override fun setState(state: Bundle?) {
        state?.getSerializable(CURRENT_MEAL)?.let { currentMeal = it as Meal }
        mealId = state?.getInt(MEAL_ID) ?: Constants.VALID_ID
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putSerializable(CURRENT_MEAL, currentMeal)
        }
    }

    override fun onDialogDismiss(dialogTag: String) {}
    override fun onDialogFinish(dialogTag: String, results: Bundle) {
        if (dialogTag != SelectFoodQuantityDialog.TAG) {
            dialogsManager.showSelectFoodQuantityDialog(results.getInt(FOOD_ID))
        } else {
            val selectedFood = results.getSerializable(SELECTED_FOOD) as Food
            val selectedFoodQuantity = results.getSerializable(SELECTED_FOOD_QUANTITY) as PhysicalQuantity
            val mealFoodFromSelectedFood = MealFood(
                id = Constants.VALID_ID,
                foodId = selectedFood.id,
                mealId = mealId,
                title = selectedFood.title,
                macroNutrients = selectedFood.macroNutrients,
                desiredServingSize = selectedFoodQuantity
            )
            currentMeal = currentMeal.copy(foods = currentMeal.foods + mealFoodFromSelectedFood)
        }
    }
}