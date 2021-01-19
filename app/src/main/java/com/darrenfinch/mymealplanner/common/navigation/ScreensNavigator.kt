package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsFragment
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsFragment
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormFragment
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragment
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanFragment
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormFragment
import com.ncapdevi.fragnav.FragNavController

class ScreensNavigator(private val navController: FragNavController) {

    private val rootFragmentListener = object : FragNavController.RootFragmentListener {
        override val numberOfRootFragments = 3

        override fun getRootFragment(index: Int): Fragment {
            return when (index) {
                FragNavController.TAB1 -> MealPlanFragment.newInstance()
                FragNavController.TAB2 -> AllMealsFragment.newInstance()
                FragNavController.TAB3 -> AllFoodsFragment.newInstance()
                else -> throw IllegalStateException("Wrong tab index: $index")
            }
        }
    }

    fun init(savedInstanceState: Bundle?) {
        navController.rootFragmentListener = rootFragmentListener
        navController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(savedInstanceState: Bundle?) {
        navController.onSaveInstanceState(savedInstanceState)
    }

    fun goBack(): Boolean {
        return if (navController.isRootFragment) {
            false
        } else {
            navController.popFragment()
            true
        }
    }

    fun switchTab(index: Int) {
        navController.switchTab(index)
    }

    fun navigateToFoodFormScreen(foodId: Int) {
        navController.pushFragment(FoodFormFragment.newInstance(foodId))
    }

    fun navigateToMealFormScreen(
        mealId: Int
    ) {
        navController.pushFragment(MealFormFragment.newInstance(mealId))
    }

    fun navigateToMealPlanFormScreen() {
        navController.pushFragment(MealPlanFormFragment.newInstance())
    }
}