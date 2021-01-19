package com.darrenfinch.mymealplanner.domain.foodform.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.foodform.view.FoodFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class FoodFormController(
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, FoodFormViewMvc.Listener, BackPressListener {

    data class SavedState(val hasLoadedFoodDetails: Boolean, val foodDetails: Food) : BaseController.BaseSavedState

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodIdArg = Constants.INVALID_ID

    private var foodDetailsState: Food = DefaultModels.defaultFood
    private var hasLoadedFoodDetailsState = false

    fun bindView(viewMvc: FoodFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
    }

    fun fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner: LifecycleOwner) {
        val shouldFetchFoodDetails = foodIdArg != Constants.INVALID_ID && !hasLoadedFoodDetailsState
        if (shouldFetchFoodDetails) {
            getFoodUseCase.fetchFood(foodIdArg).observe(viewLifecycleOwner, Observer { food ->
                foodDetailsState = food
                hasLoadedFoodDetailsState = true
                viewMvc.bindFoodDetails(food)
            })
        } else {
            viewMvc.bindFoodDetails(foodDetailsState)
        }
    }

    override fun onDoneButtonClicked(editedFoodDetails: Food) {
        if (foodIdArg != Constants.INVALID_ID)
            insertFoodUseCase.insertFood(editedFoodDetails)
        else
            updateFoodUseCase.updateFood(editedFoodDetails)
        screensNavigator.goBack()
    }

    fun setArgs(foodId: Int) {
        this.foodIdArg = foodId
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            hasLoadedFoodDetailsState = it.hasLoadedFoodDetails
            foodDetailsState = it.foodDetails
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(hasLoadedFoodDetailsState, foodDetailsState)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}