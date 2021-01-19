package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc

class MealFormFragment : BaseFragment() {

    companion object {

        // Arguments
        const val MEAL_ID_ARG = "MEAL_ID"

        // State
        const val HAS_LOADED_MEAL_DETAILS_STATE = "HAS_LOADED_MEAL_DETAILS"
        const val MEAL_DETAILS_STATE = "MEAL_DETAILS"

        fun newInstance(mealId: Int): MealFormFragment {
            val bundle = Bundle()
            bundle.putInt(MEAL_ID_ARG, mealId)
            val fragment = MealFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: MealFormController
    private lateinit var viewMvc: MealFormViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getMealFormController()
        listenForSelectFoodForMealDialogResults()
        listenForSelectFoodQuantityDialogResults()
    }

    private fun listenForSelectFoodForMealDialogResults() {
        childFragmentManager.setFragmentResultListener(SelectFoodForMealDialog.TAG, this, FragmentResultListener { requestKey, result ->
            controller.setDialogResults(requestKey, result)
        })
    }

    private fun listenForSelectFoodQuantityDialogResults() {
        childFragmentManager.setFragmentResultListener(SelectFoodQuantityDialog.TAG, this, FragmentResultListener { requestKey, result ->
            controller.setDialogResults(requestKey, result)
        })
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
