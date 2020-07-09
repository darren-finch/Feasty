package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvcImpl
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.domain.allfoods.view.AllFoodsViewMvcImpl
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.view.SelectFoodForMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodViewMvc
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvcImpl

class ViewMvcFactory(private val inflater: LayoutInflater) {
    fun getAddEditFoodViewMvc(parent: ViewGroup?, insertingFood: Boolean): AddEditFoodViewMvc = AddEditFoodViewMvcImpl(inflater, parent, insertingFood)
    fun getAddEditMealViewMvc(parent: ViewGroup?): AddEditMealViewMvc = AddEditMealViewMvcImpl(inflater, parent)
    fun getAllFoodsViewMvc(parent: ViewGroup?): AllFoodsViewMvc = AllFoodsViewMvcImpl(inflater, parent)
    fun getAllMealsViewMvc(parent: ViewGroup?): AllMealsViewMvc = AllMealsViewMvcImpl(inflater, parent)
    fun getSelectFoodForMealsViewMvc(parent: ViewGroup?) = SelectFoodForMealViewMvcImpl(inflater, parent)
    fun getSelectMealFoodQuantityViewMvc(parent: ViewGroup?): SelectMealFoodViewMvc = SelectMealFoodQuantityViewMvcImpl(inflater, parent)
}
