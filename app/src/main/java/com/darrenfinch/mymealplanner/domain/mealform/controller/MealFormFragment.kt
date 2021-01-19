package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc

class MealFormFragment : BaseFragment() {

    companion object {

        // Arguments
        const val MEAL_ID = "MEAL_ID"

        // State
        const val HAS_LOADED_MEAL_DETAILS = "HAS_LOADED_MEAL_DETAILS"
        const val MEAL_DETAILS = "MEAL_DETAILS"

        fun newInstance(mealId: Int): MealFormFragment {
            val bundle = Bundle()
            bundle.putInt(MEAL_ID, mealId)
            val fragment = MealFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: MealFormController
    private lateinit var viewMvc: MealFormViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(SelectFoodForMealDialog.TAG) { tag, results ->
            controller.setDialogResults(tag, results)
        }
        controller = fragmentCompositionRoot.getMealFormController()
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
        controller.fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner)

        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated()")
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
        println("onStart()")
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
