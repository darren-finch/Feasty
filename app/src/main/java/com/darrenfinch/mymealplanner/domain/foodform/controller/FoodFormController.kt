package com.darrenfinch.mymealplanner.domain.foodform.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
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
    private val viewModel: FoodFormViewModel
) : FoodFormViewMvc.Listener {

    private lateinit var viewMvc: FoodFormViewMvc

    private fun canFetchFoodDetails() = viewModel.isNotDirty() && !viewModel.insertingFood

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
        viewModel.setObservableFoodData(foodDetails)
        viewMvc.bindFoodDetails(viewModel.getObservableFood())
    }

    fun fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner: LifecycleOwner) {
        if (canFetchFoodDetails())
            fetchFoodDetailsFromRepository(viewLifecycleOwner)
        else
            viewMvc.bindFoodDetails(viewModel.getObservableFood())
    }

    private fun fetchFoodDetailsFromRepository(viewLifecycleOwner: LifecycleOwner) {
        getFoodUseCase.fetchFood(viewModel.foodId).observe(viewLifecycleOwner, Observer { food ->
            bindFoodDetailsToViewModelAndViewMvc(food)
        })
    }

    override fun onDoneButtonClicked(editedFoodDetails: Food) {
        saveFoodDetails(editedFoodDetails)

        screensNavigator.navigateToAllFoodsScreen()
    }

    private fun saveFoodDetails(editedFoodDetails: Food) {
        if (viewModel.insertingFood)
            insertFood(editedFoodDetails)
        else
            updateFood(editedFoodDetails)
    }

    private fun insertFood(food: Food) = insertFoodUseCase.insertFood(food)
    private fun updateFood(food: Food) = updateFoodUseCase.updateFood(food)
}