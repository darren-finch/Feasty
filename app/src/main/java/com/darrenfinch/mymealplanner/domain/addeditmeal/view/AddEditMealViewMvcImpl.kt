package com.darrenfinch.mymealplanner.domain.addeditmeal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.reusable.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAddEditMealBinding
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal

class AddEditMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<AddEditMealViewMvc.Listener>(), AddEditMealViewMvc {

    private val binding: FragmentAddEditMealBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_add_edit_meal,
        parent,
        false
    )

    private val mealFoodsRecyclerViewAdapter = MealFoodsRecyclerViewAdapter(mutableListOf())

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
            listener.doneButtonClicked()
        }
    }

    private fun addNewFoodClicked() {
        for (listener in getListeners()) {
            listener.addNewFoodButtonClicked()
        }
    }

    override fun bindMealDetails(observableMeal: ObservableMeal) {
        binding.meal = observableMeal
        mealFoodsRecyclerViewAdapter.updateFoods(observableMeal.foods)
    }
}