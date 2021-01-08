package com.darrenfinch.mymealplanner.domain.mealplan.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.mealplanmealsrecyclerviewadapter.MealPlanMealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanBinding
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal
import com.darrenfinch.mymealplanner.model.data.structs.MealPlanMacros

class MealPlanViewMvcImpl(inflater: LayoutInflater,
                          parent: ViewGroup?
) : BaseObservableViewMvc<MealPlanViewMvc.Listener>(), MealPlanViewMvc {
    private val binding: FragmentMealPlanBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_plan,
        parent,
        false
    )

    private val listener = object : MealPlanMealsRecyclerViewAdapter.ItemEventListener {
        override fun onDelete(mealPlanMeal: MealPlanMeal) {
            onDeleteMealPlanMealClicked(mealPlanMeal)
        }
    }

    private val adapter = MealPlanMealsRecyclerViewAdapter(listener)

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            mealPlanMealsRecyclerView.adapter = adapter
            mealPlanMealsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            mealPlanMealsRecyclerView.addItemDecoration(MarginItemDecoration(16))
//            addNewMealPlan.setOnClickListener { onAddNewMealPlanClicked() }
//            deleteMealPlan.setOnClickListener { onDeleteMealPlanClicked() }
            addNewMealPlanMealFAB.setOnClickListener { onAddNewMealPlanMealClicked() }
            toolbar.inflateMenu(R.menu.meal_plan_menu)
            toolbar.menu.findItem(R.id.addNewMealPlan).setOnMenuItemClickListener {
                onAddNewMealPlanClicked()
                true
            }
            toolbar.menu.findItem(R.id.deleteMealPlan).setOnMenuItemClickListener {
                onDeleteMealPlanClicked()
                true
            }
        }
    }

    private fun onAddNewMealPlanMealClicked() {
        for (listener in getListeners()) {
            listener.onAddNewMealPlanMealClicked()
        }
    }

    private fun onDeleteMealPlanClicked() {
        for(listener in getListeners()) {
            listener.onDeleteMealPlanClicked()
        }
    }

    private fun onAddNewMealPlanClicked() {
        for(listener in getListeners()) {
            listener.onAddNewMealPlanClicked()
        }
    }

    private fun onDeleteMealPlanMealClicked(mealPlanMeal: MealPlanMeal) {
        for (listener in getListeners()) {
            listener.onDeleteMealPlanMealClicked(mealPlanMeal)
        }
    }

    private fun setupSelectMealPlanSpinner(mealPlans: List<MealPlan>) {
        //TODO: This will come from the database in the future.
        val spinnerAdapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            mealPlans.map { it.title }
        )
        val spinner = binding.toolbar.menu.findItem(R.id.selectMealPlanSpinner).actionView as Spinner
        spinnerAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item)
        spinner.adapter = spinnerAdapter
        spinner.background = ResourcesCompat.getDrawable(getContext().resources, R.drawable.toolbar_spinner_bg, getContext().theme)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onMealPlanSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    private fun onMealPlanSelected(index: Int) {
        for(listeners in getListeners()) {
            listeners.onMealPlanSelected(index)
        }
    }

    override fun bindMealPlanMeals(meals: List<MealPlanMeal>) {
        binding.noMealPlanMeals.visibility = if (meals.isEmpty()) View.VISIBLE else View.GONE
        adapter.updateItems(meals)
    }

    override fun bindMealPlanMacros(mealPlanMacros: MealPlanMacros) {
        binding.mealPlanMacros = mealPlanMacros
    }

    override fun bindMealPlans(mealPlans: List<MealPlan>) {
        setupSelectMealPlanSpinner(mealPlans)
    }

    override fun setSelectedMealPlanIndex(index: Int) {
        (binding.toolbar.menu.findItem(R.id.selectMealPlanSpinner).actionView as Spinner).setSelection(index)
    }

    override fun hideMealPlans() {
        (binding.toolbar.menu.findItem(R.id.selectMealPlanSpinner).actionView as Spinner).visibility = View.GONE
    }

    override fun showMealPlans() {
        (binding.toolbar.menu.findItem(R.id.selectMealPlanSpinner).actionView as Spinner).visibility = View.VISIBLE
    }
}