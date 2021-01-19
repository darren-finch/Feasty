package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.MEAL_DETAILS
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.HAS_LOADED_MEAL_DETAILS
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
    private val dialogsManager: DialogsManager,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealFormViewMvc.Listener, BackPressListener {

    private var mealId = Constants.INVALID_ID
    private var hasLoadedMealDetails = false
    private var mealDetails: Meal? = null

    private lateinit var viewMvc: MealFormViewMvc

    private val isEditingExistingMeal: Boolean
        get() = mealId != Constants.INVALID_ID

    fun bindView(viewMvc: MealFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun setDialogResults(tag: String, results: Bundle) {
        if(tag == SelectFoodForMealDialog.TAG) {
            dialogsManager.showSelectFoodQuantityDialog(results.getInt(FOOD_ID))
        }
        else if (tag == SelectFoodQuantityDialog.TAG) {
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
            mealDetails = mealDetails!!.copy(foods = mealDetails!!.foods + mealFoodFromSelectedFood)
            viewMvc.bindMealDetails(mealDetails!!)
        }
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
    }

    fun fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner: LifecycleOwner) {
        if (isEditingExistingMeal && !hasLoadedMealDetails) {
            getMealUseCase.getMeal(mealId).observe(viewLifecycleOwner, Observer {
                mealDetails = it
                hasLoadedMealDetails = true
                viewMvc.bindMealDetails(it)
            })
        } else {
            mealDetails = DefaultModels.defaultMeal
            viewMvc.bindMealDetails(mealDetails!!)
        }
    }

    override fun onAddNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun onDoneButtonClicked(editedMealDetails: Meal) {
        if(isEditingExistingMeal) {
            updateMealUseCase.updateMeal(editedMealDetails)
        }
        else {
            insertMealUseCase.insertMeal(editedMealDetails)
        }
        screensNavigator.goBack()
    }

    override fun setState(state: Bundle?) {
        mealId = state?.getInt(MEAL_ID) ?: Constants.VALID_ID
        hasLoadedMealDetails = state?.getBoolean(HAS_LOADED_MEAL_DETAILS) ?: false
        mealDetails = state?.getSerializable(MEAL_DETAILS) as Meal?
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(MEAL_ID, mealId)
            putBoolean(HAS_LOADED_MEAL_DETAILS, hasLoadedMealDetails)
            putSerializable(MEAL_DETAILS, viewMvc.getMealDetails())
        }
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}