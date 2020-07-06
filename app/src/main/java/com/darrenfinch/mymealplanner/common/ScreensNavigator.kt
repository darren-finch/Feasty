package com.darrenfinch.mymealplanner.common

import androidx.navigation.NavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsFragmentDirections
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsFragmentDirections

class ScreensNavigator(private val navigationController: NavController)
{
    fun navigateToAddEditFoodFragment(foodId: Int) {
        val directions =
            AllFoodsFragmentDirections.actionFoodsFragmentToAddEditFoodFragment(
                foodId
            )
        navigationController.navigate(directions)
    }
    fun navigateToAddEditMealsFragment() {
        val directions = AllMealsFragmentDirections.actionMealsFragmentToAddEditMealFragment()
        navigationController.navigate(directions)
    }

    fun toAllFoods() {
        navigationController.navigate(R.id.allFoodsFragment)
    }
}