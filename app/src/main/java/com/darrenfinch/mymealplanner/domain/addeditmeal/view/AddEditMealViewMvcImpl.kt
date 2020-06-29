package com.darrenfinch.mymealplanner.domain.addeditmeal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAddEditMealBinding

class AddEditMealViewMvcImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : BaseObservableViewMvc<AddEditMealViewMvc.Listener>(), AddEditMealViewMvc {

    private val binding: FragmentAddEditMealBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_add_edit_meal,
        container,
        false
    )

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            addNewFood.setOnClickListener {
                addNewFoodClicked()
            }
        }
    }

    private fun addNewFoodClicked() {
        for (listener in getListeners()) {
            listener.addNewFoodClicked()
        }
    }
}