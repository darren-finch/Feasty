package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.domain.foodform.view.FoodFormViewMvc
import com.darrenfinch.mymealplanner.domain.foodform.view.FoodFormViewMvcImpl
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvcImpl
import com.darrenfinch.mymealplanner.domain.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.domain.allfoods.view.AllFoodsViewMvcImpl
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.view.SelectFoodForMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvcImpl

class ViewMvcFactory(private val inflater: LayoutInflater) {
    fun getAddEditFoodViewMvc(parent: ViewGroup?, insertingFood: Boolean): FoodFormViewMvc = FoodFormViewMvcImpl(inflater, parent, insertingFood)
    fun getAddEditMealViewMvc(parent: ViewGroup?): MealFormViewMvc = MealFormViewMvcImpl(inflater, parent)
    fun getAllFoodsViewMvc(parent: ViewGroup?): AllFoodsViewMvc = AllFoodsViewMvcImpl(inflater, parent)
    fun getAllMealsViewMvc(parent: ViewGroup?): AllMealsViewMvc = AllMealsViewMvcImpl(inflater, parent)
    fun getSelectFoodForMealsViewMvc(parent: ViewGroup?) = SelectFoodForMealViewMvcImpl(inflater, parent)
    fun getSelectMealFoodQuantityViewMvc(parent: ViewGroup?): SelectMealFoodQuantityViewMvc = SelectMealFoodQuantityViewMvcImpl(inflater, parent)
}
