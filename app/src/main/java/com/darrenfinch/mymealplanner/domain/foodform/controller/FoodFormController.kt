package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormFragment.Companion.FOOD_DETAILS
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormFragment.Companion.FOOD_ID
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormFragment.Companion.HAS_LOADED_FOOD_DETAILS
import com.darrenfinch.mymealplanner.domain.foodform.view.FoodFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food

class FoodFormController(
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase
) : BaseController, FoodFormViewMvc.Listener {

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodId = -1
    private var foodDetails: Food? = null
    private var hasLoadedFoodDetails = false // TODO: Remove when view models are added

    private val insertingFood: Boolean
        get() = foodId < 0
    private val canFetchFoodDetails: Boolean
        get() = !hasLoadedFoodDetails && !insertingFood

    fun bindView(viewMvc: FoodFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    private fun bindFoodDetailsToViewModelAndViewMvc(foodDetails: Food) {
        this.foodDetails = foodDetails
        viewMvc.bindFoodDetails(foodDetails)
    }

    fun fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner: LifecycleOwner) {
        if (canFetchFoodDetails) {
            getFoodUseCase.fetchFood(foodId).observe(viewLifecycleOwner, Observer { food ->
                hasLoadedFoodDetails = true
                bindFoodDetailsToViewModelAndViewMvc(food)
            })
        }
        else {
            foodDetails?.let {
                viewMvc.bindFoodDetails(it)
            }
        }
    }

    override fun onDoneButtonClicked(editedFoodDetails: Food) {
        if (insertingFood)
            insertFoodUseCase.insertFood(editedFoodDetails)
        else
            updateFoodUseCase.updateFood(editedFoodDetails)
        screensNavigator.goBack()
    }

    override fun setState(state: Bundle?) {
        foodId = state?.getInt(FOOD_ID) ?: -1
        foodDetails = state?.getSerializable(FOOD_DETAILS) as Food?
        hasLoadedFoodDetails = state?.getBoolean(HAS_LOADED_FOOD_DETAILS) ?: false
    }
    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(FOOD_ID, foodId)
            putSerializable(FOOD_DETAILS, viewMvc.getFoodDetails())
            putBoolean(HAS_LOADED_FOOD_DETAILS, hasLoadedFoodDetails)
        }
    }
}