package com.darrenfinch.mymealplanner.screens.allmeals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealslist.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAllMealsBinding
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

class AllMealsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<AllMealsViewMvc.Listener>(), AllMealsViewMvc {
    private val mealItemEventListener = object : MealsRecyclerViewAdapter.ItemEventListener {
        override fun onSelect(meal: UiMeal) { }

        override fun onEdit(mealId: Int) {
            for (listener in getListeners()) {
                listener.onMealEdit(mealId)
            }
        }

        override fun onDelete(meal: UiMeal) {
            for (listener in getListeners()) {
                listener.onMealDelete(meal)
            }
        }

    }

    private val mealsRecyclerViewAdapter =
        MealsRecyclerViewAdapter(MealsRecyclerViewAdapter.Config(), mealItemEventListener)

    private var _binding: FragmentAllMealsBinding? = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_all_meals,
        parent,
        false
    )
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            mealsRecyclerView.adapter = mealsRecyclerViewAdapter
            mealsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            mealsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    16
                )
            )
            addMealFab.setOnClickListener { onAddNewMealClicked() }
        }
    }

    private fun onAddNewMealClicked() {
        for(listener in getListeners()) {
            listener.addNewMealClicked()
        }
    }

    override fun bindMeals(newMeals: List<UiMeal>) {
        mealsRecyclerViewAdapter.updateItems(newMeals)
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.mealsRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.mealsRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}