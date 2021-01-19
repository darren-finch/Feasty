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
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.MEAL_DETAILS_STATE
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.HAS_LOADED_MEAL_DETAILS_STATE
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment.Companion.MEAL_ID_ARG
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

// TODO: Think about whether it would be a good idea to make these conventions project-wide:
// For navigation arguments (whether they are saved on config changes or not), name them in the form of xArg
// For saved state variables, name them in the form of yState
class MealFormController(
    private val insertMealUseCase: InsertMealUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealFormViewMvc.Listener, BackPressListener {

    private var mealIdArg = Constants.INVALID_ID
    private var hasLoadedMealDetailsState = false
    private var mealDetailsState: Meal = DefaultModels.defaultMeal

    private lateinit var viewMvc: MealFormViewMvc

    private val isEditingExistingMeal: Boolean
        get() = mealIdArg != Constants.INVALID_ID

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
                mealId = mealDetailsState.id,
                title = selectedFood.title,
                macroNutrients = selectedFood.macroNutrients,
                desiredServingSize = selectedFoodQuantity
            )
            mealDetailsState = mealDetailsState.copy(foods = mealDetailsState.foods + mealFoodFromSelectedFood)
            viewMvc.bindMealDetails(mealDetailsState)
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
        if (isEditingExistingMeal && !hasLoadedMealDetailsState) {
            getMealUseCase.getMeal(mealIdArg).observe(viewLifecycleOwner, Observer {
                mealDetailsState = it
                hasLoadedMealDetailsState = true
                viewMvc.bindMealDetails(it)
            })
        } else {
            viewMvc.bindMealDetails(mealDetailsState)
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
        mealIdArg = state?.getInt(MEAL_ID_ARG) ?: Constants.INVALID_ID
        hasLoadedMealDetailsState = state?.getBoolean(HAS_LOADED_MEAL_DETAILS_STATE) ?: false
        state?.getSerializable(MEAL_DETAILS_STATE)?.let { mealDetailsState = it as Meal }
    }

    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(MEAL_ID_ARG, mealIdArg)
            putBoolean(HAS_LOADED_MEAL_DETAILS_STATE, hasLoadedMealDetailsState)
            putSerializable(MEAL_DETAILS_STATE, viewMvc.getMealDetails())
        }
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}