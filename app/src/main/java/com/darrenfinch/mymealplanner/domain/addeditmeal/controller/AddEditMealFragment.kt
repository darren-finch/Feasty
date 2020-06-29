package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealDialog

class AddEditMealFragment : Fragment(), AddEditMealViewMvc.Listener {

    private lateinit var viewMvc: AddEditMealViewMvc

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewMvc = AddEditMealViewMvcImpl(inflater, container)
        return viewMvc.getRootView()
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun addNewFoodClicked() {
        val selectFoodForMealDialog = SelectFoodForMealDialog()
        selectFoodForMealDialog.show(childFragmentManager, "SelectFoodForMealDialog")
    }
}
