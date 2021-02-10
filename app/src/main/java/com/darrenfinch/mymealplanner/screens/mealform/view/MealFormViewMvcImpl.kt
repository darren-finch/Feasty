package com.darrenfinch.mymealplanner.screens.mealform.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealfoodslist.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealFormBinding
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFormViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<MealFormViewMvc.Listener>(), MealFormViewMvc {

    private var _binding: FragmentMealFormBinding? = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_form,
        parent,
        false
    )
    private val binding = _binding!!

    private val mealFoodsAdapterListener = object : MealFoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemEdit(mealFood: UiMealFood, index: Int) {
            for (listener in getListeners()) {
                listener.onMealFoodEdit(mealFood, index)
            }
        }

        override fun onItemDelete(index: Int) {
            for (listener in getListeners()) {
                listener.onMealFoodDelete(index)
            }
        }
    }

    private val mealFoodsRecyclerViewAdapter = MealFoodsRecyclerViewAdapter(
        MealFoodsRecyclerViewAdapter.Config(showAsFullCard = true),
        listener = mealFoodsAdapterListener
    )

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            addNewFood.setOnClickListener {
                onAddNewFoodButtonClicked()
            }
            toolbar.setNavigationOnClickListener {
                onNavigateUp()
            }
            doneButton.setOnClickListener {
                onDoneButtonClicked()
            }

            mealNameEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onTitleChange(text.toString())
                }
            }

            mealFoodsRecyclerView.adapter = mealFoodsRecyclerViewAdapter
            mealFoodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            mealFoodsRecyclerView.addItemDecoration(MarginItemDecoration(16))
        }
    }

    private fun onDoneButtonClicked() {
        for (listener in getListeners()) {
            listener.onDoneButtonClicked()
        }
    }

    private fun onAddNewFoodButtonClicked() {
        for (listener in getListeners()) {
            listener.onAddNewFoodButtonClicked()
        }
    }

    private fun onNavigateUp() {
        for (listener in getListeners()) {
            listener.onNavigateUp()
        }
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun bindMealDetails(mealDetails: UiMeal) {
        binding.meal = mealDetails
        mealFoodsRecyclerViewAdapter.updateItems(mealDetails.foods)
    }

    override fun showProgressIndication() {
        binding.formInputsGroup.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.formInputsGroup.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}