package com.darrenfinch.mymealplanner.domain.allmeals.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAllMealsBinding
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class AllMealsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<AllMealsViewMvc.Listener>(), AllMealsViewMvc {
    private val mealItemEventListener = object : MealsRecyclerViewAdapter.ItemEventListener {
        override fun onSelect(meal: Meal) { }

        override fun onEdit(mealId: Int) {
            for (listener in getListeners()) {
                listener.onMealEdit(mealId)
            }
        }

        override fun onDelete(meal: Meal) {
            for (listener in getListeners()) {
                listener.onMealDelete(meal)
            }
        }

    }

    private val mealsRecyclerViewAdapter =
        MealsRecyclerViewAdapter(MealsRecyclerViewAdapter.Config(), mealItemEventListener)

    private val binding: FragmentAllMealsBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_all_meals,
        parent,
        false
    )

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

            addNewMeal.setOnClickListener { notifyListenersOfAddNewMealClicked() }
        }
    }

    private fun notifyListenersOfAddNewMealClicked() {
        for(listener in getListeners()) {
            listener.addNewMealClicked()
        }
    }

    override fun bindMeals(newMeals: List<Meal>) {
        mealsRecyclerViewAdapter.updateItems(newMeals)
    }
}