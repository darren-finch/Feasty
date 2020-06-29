package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc

class AddEditFoodFragment : BaseFragment() {

    private val CONTROLLER_STATE_EXTRA = "CONTROLLER_STATE_EXTRA"

    private lateinit var controller: AddEditFoodController

    private val args: AddEditFoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewMvc = initViewMvc(container)
        initController(viewMvc, savedInstanceState)

        viewMvc.bindFoodDetails(controller.getObservableFood())

        return viewMvc.getRootView()
    }

    private fun initViewMvc(container: ViewGroup?) = controllerCompositionRoot.getViewMvcFactory().getAddEditFoodViewMvc(container, args.foodId < 0)

    private fun initController(viewMvc: AddEditFoodViewMvc, savedInstanceState: Bundle?) {
        //It's ok to manually instantiate this. The only reason we have a controller in the first place is to make this fragment testable (in the future maybe ha-ha).
        controller = AddEditFoodController(controllerCompositionRoot.getFoodsRepository(), args.foodId)
        controller.bindView(viewMvc)

        restoreSavedControllerState(savedInstanceState)

        //fetchFoodIfPossible() overrides saved controller state if called before restoreSavedControllerState()
        controller.fetchFoodIfPossible(viewLifecycleOwner)
    }

    private fun restoreSavedControllerState(savedInstanceState: Bundle?) {
        if(savedInstanceState != null)
            controller.restoreSavedState(savedInstanceState.getSerializable(CONTROLLER_STATE_EXTRA) as AddEditFoodController.SavedState)
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
        outState.putSerializable(CONTROLLER_STATE_EXTRA, controller.getSavedState())
    }
}
