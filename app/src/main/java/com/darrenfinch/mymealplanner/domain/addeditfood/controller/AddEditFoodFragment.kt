package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvcImpl
import com.darrenfinch.mymealplanner.model.data.Food

class AddEditFoodFragment : Fragment(), AddEditFoodViewMvc.Listener {

    private lateinit var viewMvc: AddEditFoodViewMvc

    private val viewModel: AddEditFoodViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    private val args: AddEditFoodFragmentArgs by navArgs()

    private val insertingFood: Boolean by lazy {
        args.foodId < 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewMvc(inflater, container)
        if (canFetchFoodDetails()) {
            fetchFoodDetails()
        }
        return viewMvc.getRootView()
    }

    private fun canFetchFoodDetails() = !insertingFood && !viewModel.getObservableFood().dirty

    private fun initViewMvc(inflater: LayoutInflater, container: ViewGroup?) {
        viewMvc = AddEditFoodViewMvcImpl(inflater, container, insertingFood)
        viewMvc.bindFoodDetails(viewModel.getObservableFood())
    }

    private fun fetchFoodDetails() {
        viewModel.fetchFood(args.foodId, viewLifecycleOwner)
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onDoneButtonClicked(editedFoodDetails: Food) {
        saveFoodDetails(editedFoodDetails)
        findNavController().navigate(R.id.allFoodsFragment)
    }

    private fun saveFoodDetails(editedFoodDetails: Food) {
        if (insertingFood) viewModel.insertFood(editedFoodDetails) else viewModel.updateFood(editedFoodDetails)
    }
}
