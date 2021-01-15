package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityDialog.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.CURRENT_MEAL
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.MEAL_ID
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.NEW_MEAL_FOOD
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFormController(
    private val viewModel: MealFormViewModel,
    private val insertMealUseCase: InsertMealUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager
) : BaseController, MealFormViewMvc.Listener, DialogsManager.OnDialogEventListener {

    private var newMealFood: MealFood? = null
    private var currentMeal: Meal? = null
    private var mealId = Constants.DEFAULT_INVALID_ID

    private lateinit var viewMvc: MealFormViewMvc

    private val isEditingExistingMeal: Boolean
        get() = mealId != Constants.DEFAULT_INVALID_ID
    private val isEditingMealForTheFirstTime: Boolean
        get() = currentMeal == null && newMealFood == null

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
        if (isEditingExistingMeal && isEditingMealForTheFirstTime) {
            getMealUseCase.getMeal(mealId).observe(viewLifecycleOwner, Observer {
                viewModel.setObservableMeal(it)
                bindObservableMealToView()
            })
        } else {
            addNewMealFoodToCurrentMeal()
            bindObservableMealToView()
        }
    }

    private fun bindObservableMealToView() {
        viewMvc.bindMealDetails(viewModel.getObservableMeal())
    }

    private fun addNewMealFoodToCurrentMeal() {
        if (currentMeal != null && newMealFood != null) {
            val updatedMeal =
                Meal(
                    currentMeal!!.id,
                    currentMeal!!.title,
                    currentMeal!!.foods.toMutableList().apply {
                        add(newMealFood!!)
                    })
            viewModel.setObservableMeal(updatedMeal)
        }
    }

    override fun addNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun doneButtonClicked() {
        screensNavigator.goBack()
        val finalMeal = viewModel.getObservableMeal().get()
        if (!isEditingExistingMeal)
            insertMealUseCase.insertMeal(finalMeal)
        else
            updateMealUseCase.updateMeal(finalMeal)
    }

    override fun setState(state: Bundle?) {
        newMealFood = state?.getSerializable(NEW_MEAL_FOOD) as MealFood?
        currentMeal = state?.getSerializable(CURRENT_MEAL) as Meal?
        mealId = state?.getInt(MEAL_ID) ?: Constants.DEFAULT_VALID_ID
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putSerializable(NEW_MEAL_FOOD, newMealFood)
            putSerializable(CURRENT_MEAL, currentMeal)
            putSerializable(MEAL_ID, mealId)
        }
    }

    override fun onDialogDismiss(dialogTag: String) {}
    override fun onDialogFinish(dialogTag: String, results: Bundle) {
        if (dialogTag != SelectMealFoodQuantityDialog.TAG) {
            dialogsManager.showSelectMealFoodQuantityDialog(results.getInt(FOOD_ID), mealId)
        } else {
            val newMealFood = results.getSerializable(NEW_MEAL_FOOD) as MealFood
            val currentMeal = viewModel.getObservableMeal().get()
            // TODO: For the love of everything beautiful fix this when view models are removed
            viewModel.setObservableMeal(currentMeal.copy(foods = currentMeal.foods.toMutableList().apply { add(newMealFood) }))
            viewMvc.bindMealDetails(viewModel.getObservableMeal())
        }
    }
}