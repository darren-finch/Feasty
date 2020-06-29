package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food
import java.io.Serializable

class AddEditFoodController(
    private val repository: FoodsRepository,
    private val foodId: Int
) : AddEditFoodViewMvc.Listener {

    private lateinit var viewMvc: AddEditFoodViewMvc
    private val insertingFood = foodId < 0

    private fun canFetchFoodDetails() = !insertingFood && !getObservableFood().dirty

    private val observableFood = ObservableFood()
    fun getObservableFood() = observableFood

    fun bindView(viewMvc: AddEditFoodViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun getSavedState(): SavedState {
        return SavedState(observableFood.get())
    }

    fun restoreSavedState(savedState: SavedState) {
        observableFood.set(savedState.foodDetails)
        viewMvc.bindFoodDetails(observableFood)
    }

    fun fetchFoodIfPossible(viewLifecycleOwner: LifecycleOwner) {
        if (canFetchFoodDetails())
            fetchFoodDetailsFromRepository(viewLifecycleOwner)
    }

    private fun fetchFoodDetailsFromRepository(viewLifecycleOwner: LifecycleOwner) {
        repository.fetchFood(foodId).observe(viewLifecycleOwner, Observer { food ->
            observableFood.set(food)
        })
    }

    override fun onDoneButtonClicked(editedFoodDetails: Food) {
        saveFoodDetails(editedFoodDetails)

        //TODO: This is a hack, please remove once you change navigation options
        findNavController(viewMvc.getRootView()).navigate(R.id.allFoodsFragment)
    }

    private fun saveFoodDetails(editedFoodDetails: Food) {
        if (insertingFood)
            insertFood(editedFoodDetails)
        else
            updateFood(editedFoodDetails)
    }

    private fun insertFood(food: Food) = repository.insertFood(food)
    private fun updateFood(food: Food) = repository.updateFood(food)

    data class SavedState(val foodDetails: Food) : Serializable
}