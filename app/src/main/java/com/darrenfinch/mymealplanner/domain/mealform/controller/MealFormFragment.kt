package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFormFragment : BaseFragment() {

    companion object {
        const val NEW_MEAL_FOOD = "MEAL_FOOD"
        const val CURRENT_MEAL = "CURRENT_MEAL"
        const val MEAL_ID = "MEAL_ID"

        fun newInstance(newMealFood: MealFood?, currentMeal: Meal?, mealId: Int): MealFormFragment {
            val bundle = Bundle()
            bundle.putSerializable(NEW_MEAL_FOOD, newMealFood)
            bundle.putSerializable(CURRENT_MEAL, currentMeal)
            bundle.putSerializable(MEAL_ID, mealId)
            val fragment = MealFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: MealFormViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private lateinit var controller: MealFormController
    private lateinit var viewMvc: MealFormViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getMealFormController(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealFormViewMvc(container)

        controller.setState(savedInstanceState ?: requireArguments())
        controller.bindView(viewMvc)

        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(viewLifecycleOwner)
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(controller.getState())
    }
}
