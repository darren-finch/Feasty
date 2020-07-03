package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetSingleFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.model.data.Food

class AddEditFoodController(
    private val getSingleFoodUseCase: GetSingleFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val viewModel: AddEditFoodViewModel
) : AddEditFoodViewMvc.Listener {

    private lateinit var viewMvc: AddEditFoodViewMvc

    private fun canFetchFoodDetails() = !viewModel.insertingFood && !viewModel.getObservableFood().dirty

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

        //TODO: This is a hack, please remove once you change navigation options
        findNavController(viewMvc.getRootView()).navigate(R.id.allFoodsFragment)
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