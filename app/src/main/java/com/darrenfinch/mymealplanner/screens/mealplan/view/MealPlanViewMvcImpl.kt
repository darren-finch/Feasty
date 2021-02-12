package com.darrenfinch.mymealplanner.screens.mealplan.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.lists.mealslist.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanBinding
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros

class MealPlanViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<MealPlanViewMvc.Listener>(), MealPlanViewMvc {
    private var _binding: FragmentMealPlanBinding? = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_plan,
        parent,
        false
    )
    private val binding = _binding!!

    private var notifyWhenMealPlanSelected = true

    private val listener = object : MealsRecyclerViewAdapter.ItemEventListener {
        override fun onSelect(meal: UiMeal) {}
        override fun onEdit(mealId: Int) {}

        override fun onDelete(mealId: Int) {
            // TODO: THIS IS SCREWED UP. SHOULD BE MEAL PLAN MEAL ID
            onDeleteMealPlanMealClicked(mealId)
        }
    }

    private val adapter = MealsRecyclerViewAdapter(
        MealsRecyclerViewAdapter.Config(allowEditingItems = false),
        listener
    )

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            mealPlanMealsRecyclerView.adapter = adapter
            mealPlanMealsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            mealPlanMealsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            addMealPlanMealFab.setOnClickListener { onAddNewMealPlanMealClicked() }

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
        for (listener in getListeners()) {
            listener.onDeleteMealPlanClicked()
        }
    }

    private fun onAddNewMealPlanClicked() {
        for (listener in getListeners()) {
            listener.onAddNewMealPlanClicked()
        }
    }

    private fun onDeleteMealPlanMealClicked(mealPlanMealId: Int) {
        for (listener in getListeners()) {
            listener.onDeleteMealPlanMealClicked(mealPlanMealId)
        }
    }

    private fun setupSelectMealPlanSpinner(mealPlans: List<UiMealPlan>) {
        val spinnerAdapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            mealPlans.map { it.title }
        )
        spinnerAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item)
        binding.selectMealPlanSpinner.adapter = spinnerAdapter
        setSelectMealPlanSpinnerItemSelectListener()
    }

    private fun setSelectMealPlanSpinnerItemSelectListener() {
        binding.selectMealPlanSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (notifyWhenMealPlanSelected) {
                        onMealPlanSelected(position)
                    } else {
                        notifyWhenMealPlanSelected = true
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun onMealPlanSelected(index: Int) {
        for (listeners in getListeners()) {
            listeners.onMealPlanSelected(index)
        }
    }

    override fun bindMealPlanMeals(meals: List<UiMealPlanMeal>) {
        adapter.updateItems(meals.map {
            UiMeal(
                id = it.id,
                title = it.title,
                foods = it.foods
            )
        })
    }

    override fun bindMealPlanMacros(mealPlanMacros: MealPlanMacros) {
        binding.mealPlanMacros = mealPlanMacros
    }

    override fun bindMealPlans(mealPlans: List<UiMealPlan>) {
        setupSelectMealPlanSpinner(mealPlans)
    }

    override fun setSelectedMealPlanIndex(index: Int) {
        notifyWhenMealPlanSelected = true
        binding.selectMealPlanSpinner.setSelection(index)
    }

    override fun setSelectedMealPlanIndexWithoutNotifying(index: Int) {
        notifyWhenMealPlanSelected = false
        binding.selectMealPlanSpinner.setSelection(index)
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
        binding.mealPlanMealsRecyclerView.visibility = View.GONE
    }

    override fun hideProgressIndication() {
        binding.progressBar.visibility = View.GONE
        binding.mealPlanMealsRecyclerView.visibility = View.VISIBLE
    }

    override fun showEmptyListIndication() {
        binding.noMealPlanMeals.visibility = View.VISIBLE
        binding.mealPlanMealsRecyclerView.visibility = View.GONE
    }

    override fun hideEmptyListIndication() {
        binding.noMealPlanMeals.visibility = View.GONE
        binding.mealPlanMealsRecyclerView.visibility = View.VISIBLE
    }
}