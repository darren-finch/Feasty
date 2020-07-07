package com.darrenfinch.mymealplanner.common

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsFragmentDirections
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsFragmentDirections
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.model.data.Food

class ScreensNavigator(private val navigationController: NavController)
{
    fun navigateToAddEditFoodScreen(foodId: Int) {
        val directions =
            AllFoodsFragmentDirections.actionFoodsFragmentToAddEditFoodFragment(
                foodId
            )
        navigationController.navigate(directions)
    }
    fun navigateToAddEditMealsScreen() {
        val directions = AllMealsFragmentDirections.actionMealsFragmentToAddEditMealFragment()
        navigationController.navigate(directions)
    }

    fun navigateToAllFoodsScreen() {
        navigationController.navigate(R.id.allFoodsFragment)
    }

    fun navigateToSelectFoodForMealScreen() {
        navigationController.navigate(R.id.selectFoodForMealDialog)
    }

    fun navigateToSelectFoodQuantityScreen(foodId: Int) {
        //TODO(): Pass foodId to select food quantity screen
        navigationController.popBackStack()
        navigationController.navigate(R.id.selectMealFoodQuantityDialog)
    }
}