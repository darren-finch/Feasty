package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc

class MealFormFragment : BaseFragment() {

    companion object {

        // Arguments
        const val MEAL_ID_ARG = "MEAL_ID_ARG"

        // State
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

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
        childFragmentManager.setFragmentResultListener(
            SelectFoodForMealDialog.TAG,
            this,
            FragmentResultListener { requestKey, result ->
                controller.setDialogResults(requestKey, result)
            })
    }

    private fun listenForSelectFoodQuantityDialogResults() {
        childFragmentManager.setFragmentResultListener(
            SelectFoodQuantityDialog.TAG,
            this,
            FragmentResultListener { requestKey, result ->
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

        controller.bindView(viewMvc)
        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner)

        return viewMvc.getRootView()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(arguments.getInt(MEAL_ID_ARG))
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as MealFormController.SavedState)
        }
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
        outState.putAll(bundleOf(CONTROLLER_SAVED_STATE to controller.getState()))
    }
}
