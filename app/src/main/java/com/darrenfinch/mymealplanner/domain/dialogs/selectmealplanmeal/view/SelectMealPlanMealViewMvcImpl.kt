package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealPlanMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectMealPlanMealViewMvc.Listener>(), SelectMealPlanMealViewMvc {
    private val binding: FragmentSelectItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_item, parent, false)

    private val mealsListItemEventListener = object : MealsRecyclerViewAdapter.ItemEventListener {
        override fun onSelect(meal: Meal) {
            onMealSelected(meal)
        }

        override fun onEdit(mealId: Int) { }
        override fun onDelete(meal: Meal) { }
    }

    private fun onMealSelected(meal: Meal) {
        for (listener in getListeners()) {
            listener.onMealSelected(meal)
        }
    }

    private val adapter = MealsRecyclerViewAdapter(MealsRecyclerViewAdapter.Config(showViewMoreButton = false), mealsListItemEventListener)

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            itemsRecyclerView.adapter = adapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            itemsRecyclerView.addItemDecoration(MarginItemDecoration(16))
        }
    }

    override fun bindMeals(meals: List<Meal>) {
        adapter.updateItems(meals)
    }

    override fun makeDialog(): Dialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.select_meal))
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }
}