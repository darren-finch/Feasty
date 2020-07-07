package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetSingleFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.model.data.Food

class AddEditFoodController(
    private val screensNavigator: ScreensNavigator,
    private val getSingleFoodUseCase: GetSingleFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val viewModel: AddEditFoodViewModel
) : AddEditFoodViewMvc.Listener {

    private lateinit var viewMvc: AddEditFoodViewMvc

    private fun canFetchFoodDetails() = viewModel.isNotDirty()

    fun bindView(viewMvc: AddEditFoodViewMvc) {
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

    fun fetchFoodDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner: LifecycleOwner) {
        if (canFetchFoodDetails())
            fetchFoodDetailsFromRepository(viewLifecycleOwner)
        else
            viewMvc.bindFoodDetails(viewModel.getObservableFood())
    }

    private fun fetchFoodDetailsFromRepository(viewLifecycleOwner: LifecycleOwner) {
        getSingleFoodUseCase.fetchSingleFood(viewModel.foodId).observe(viewLifecycleOwner, Observer { food ->
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