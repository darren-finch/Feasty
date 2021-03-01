package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.lists.mealslist.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectItemBinding
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

class SelectMealPlanMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectMealPlanMealViewMvc.Listener>(), SelectMealPlanMealViewMvc {
    private var _binding: FragmentSelectItemBinding? = DataBindingUtil.inflate(inflater, R.layout.fragment_select_item, parent, false)
    private val binding = _binding!!

    private val mealsListItemEventListener = object : MealsRecyclerViewAdapter.ItemEventListener {
        override fun onSelect(meal: UiMeal) {
            onMealChosen(meal)
        }

        override fun onEdit(mealId: Int) { }
        override fun onDelete(mealId: Int) {
            onDelete(mealId)
        }
    }

    private fun onMealChosen(meal: UiMeal) {
        for (listener in getListeners()) {
            listener.onMealChosen(meal)
        }
    }

    private val adapter = MealsRecyclerViewAdapter(MealsRecyclerViewAdapter.Config(showViewMoreButton = false), mealsListItemEventListener)

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                for (listener in getListeners()) {
                    listener.onNavigateUp()
                }
            }
            toolbar.setTitle(R.string.select_meal)

            itemsRecyclerView.adapter = adapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            itemsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        for (listener in getListeners()) {
                            listener.onQuerySubmitted(query)
                        }
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // no-op
                    return false
                }
            })
        }
    }

    override fun setQuery(query: String) {
        binding.searchView.setQuery(query, false)
    }

    override fun bindMeals(meals: List<UiMeal>) {
        adapter.updateItems(meals)
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }
}