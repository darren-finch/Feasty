package com.darrenfinch.mymealplanner.common.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvcImpl
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvcImpl

class ViewMvcFactory(private val inflater: LayoutInflater) {
    fun getAddEditFoodViewMvc(parent: ViewGroup?, insertingFood: Boolean): AddEditFoodViewMvc = AddEditFoodViewMvcImpl(inflater, parent, insertingFood)
    fun getAddEditMealViewMvc(parent: ViewGroup?): AddEditMealViewMvc = AddEditMealViewMvcImpl(inflater, parent)
}
