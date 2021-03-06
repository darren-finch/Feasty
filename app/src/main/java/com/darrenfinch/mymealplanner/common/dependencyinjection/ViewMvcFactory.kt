package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvcImpl
import com.darrenfinch.mymealplanner.common.dialogs.prompt.view.PromptViewMvc
import com.darrenfinch.mymealplanner.common.dialogs.prompt.view.PromptViewMvcImpl
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvcImpl
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvcImpl
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvcImpl
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvcImpl
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvcImpl
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvcImpl
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvcImpl
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvcImpl

class ViewMvcFactory(private val inflater: LayoutInflater) {
    fun getFoodFormViewMvc(parent: ViewGroup?): FoodFormViewMvc = FoodFormViewMvcImpl(inflater, parent)
    fun getMealFormViewMvc(parent: ViewGroup?): MealFormViewMvc = MealFormViewMvcImpl(inflater, parent)
    fun getAllFoodsViewMvc(parent: ViewGroup?): AllFoodsViewMvc = AllFoodsViewMvcImpl(inflater, parent)
    fun getAllMealsViewMvc(parent: ViewGroup?): AllMealsViewMvc = AllMealsViewMvcImpl(inflater, parent)
    fun getMealPlanViewMvc(parent: ViewGroup?): MealPlanViewMvc = MealPlanViewMvcImpl(inflater, parent)
    fun getMealPlanFormViewMvc(parent: ViewGroup?): MealPlanFormViewMvc = MealPlanFormViewMvcImpl(inflater, parent)
    fun getSelectFoodForMealViewMvc(parent: ViewGroup?): SelectFoodForMealViewMvc = SelectFoodForMealViewMvcImpl(inflater, parent)
    fun getSelectMealPlanMealViewMvc(parent: ViewGroup?): SelectMealPlanMealViewMvc = SelectMealPlanMealViewMvcImpl(inflater, parent)
    fun getEditMealViewMvc(parent: ViewGroup?): EditMealFoodViewMvc = EditMealFoodViewMvcImpl(inflater, parent)
    fun getPromptViewMvc(parent: ViewGroup?): PromptViewMvc = PromptViewMvcImpl(inflater, parent)
}
