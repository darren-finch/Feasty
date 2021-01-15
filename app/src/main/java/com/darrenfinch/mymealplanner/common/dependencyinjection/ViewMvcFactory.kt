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
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvcImpl
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvcImpl
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvcImpl
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvcImpl

class ViewMvcFactory(private val inflater: LayoutInflater) {
    fun getFoodFormViewMvc(parent: ViewGroup?): FoodFormViewMvc = FoodFormViewMvcImpl(inflater, parent)
    fun getMealFormViewMvc(parent: ViewGroup?): MealFormViewMvc = MealFormViewMvcImpl(inflater, parent)
    fun getAllFoodsViewMvc(parent: ViewGroup?): AllFoodsViewMvc = AllFoodsViewMvcImpl(inflater, parent)
    fun getAllMealsViewMvc(parent: ViewGroup?): AllMealsViewMvc = AllMealsViewMvcImpl(inflater, parent)
    fun getSelectMealFoodQuantityViewMvc(parent: ViewGroup?): SelectMealFoodQuantityViewMvc = SelectMealFoodQuantityViewMvcImpl(inflater, parent)
    fun getMealPlanViewMvc(parent: ViewGroup?) = MealPlanViewMvcImpl(inflater, parent)
    fun getMealPlanFormViewMvc(parent: ViewGroup?) = MealPlanFormViewMvcImpl(inflater, parent)
    fun getSelectFoodForMealViewMvc(parent: ViewGroup?) = SelectFoodForMealViewMvcImpl(inflater, parent)
    fun getSelectMealPlanMealViewMvc(parent: ViewGroup?) = SelectMealPlanMealViewMvcImpl(inflater, parent)
}
