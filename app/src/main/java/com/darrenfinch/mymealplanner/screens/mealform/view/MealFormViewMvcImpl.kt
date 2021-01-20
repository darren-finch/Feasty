package com.darrenfinch.mymealplanner.screens.mealform.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealFormBinding
import com.darrenfinch.mymealplanner.meals.models.Meal

class MealFormViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<MealFormViewMvc.Listener>(), MealFormViewMvc {

    private val binding: FragmentMealFormBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_form,
        parent,
        false
    )

    private val mealFoodsRecyclerViewAdapter = MealFoodsRecyclerViewAdapter()

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            addNewFood.setOnClickListener {
                addNewFoodClicked()
            }

            mealFoodsRecyclerView.adapter = mealFoodsRecyclerViewAdapter
            mealFoodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            mealFoodsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            doneButton.setOnClickListener { onDoneButtonClicked() }
        }
    }

    private fun onDoneButtonClicked() {
        for (listener in getListeners()) {
            listener.onDoneButtonClicked(getMealDetails())
        }
    }

    override fun getMealDetails(): Meal {
        return Meal(
            id = binding.meal?.id ?: Constants.VALID_ID,
            title = binding.mealNameEditText.text.toString(),
            foods = binding.meal?.foods ?: listOf()
        )
    }

    private fun addNewFoodClicked() {
        for (listener in getListeners()) {
            listener.onAddNewFoodButtonClicked()
        }
    }

    override fun bindMealDetails(mealDetails: Meal) {
        binding.meal = mealDetails
        mealFoodsRecyclerViewAdapter.updateItems(mealDetails.foods)
    }
}