package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.darrenfinch.mymealplanner.common.misc.BaseObservable
import com.darrenfinch.mymealplanner.screens.allfoods.controller.AllFoodsFragment
import com.darrenfinch.mymealplanner.screens.allmeals.controller.AllMealsFragment
import com.darrenfinch.mymealplanner.screens.foodform.controller.FoodFormFragment
import com.darrenfinch.mymealplanner.screens.mealform.controller.MealFormFragment
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment
import com.darrenfinch.mymealplanner.screens.mealplanform.controller.MealPlanFormFragment
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

class ScreensNavigator(
    private val navController: FragNavController,
    private val defaultTransactionOptions: FragNavTransactionOptions
) {

    companion object {
        private const val CUR_SCREEN_RESULT = "CUR_SCREEN_RESULT"
    }

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

    fun onSaveInstanceState(outState: Bundle) {
        navController.onSaveInstanceState(outState)
    }

    fun navigateUp(): Boolean {
        return if (navController.isRootFragment) {
            false
        } else {
            navController.popFragment(
                defaultTransactionOptions
            )
            true
        }
    }

    fun switchTab(index: Int) {
        navController.switchTab(
            index,
            defaultTransactionOptions
        )
    }

    fun toFoodFormScreen(foodId: Int) {
        navController.pushFragment(
            FoodFormFragment.newInstance(foodId),
            defaultTransactionOptions
        )
    }

    fun toMealFormScreen(
        mealId: Int
    ) {
        navController.pushFragment(
            MealFormFragment.newInstance(mealId),
            defaultTransactionOptions
        )
    }

    fun toMealPlanFormScreen() {
        navController.pushFragment(
            MealPlanFormFragment.newInstance(),
            defaultTransactionOptions
        )
    }

    fun toSelectFoodForMealScreen() {
        navController.pushFragment(
            SelectFoodForMealFragment.newInstance(),
            defaultTransactionOptions
        )
    }

    fun toSelectMealPlanMealScreen() {
        navController.pushFragment(
            SelectMealPlanMealFragment.newInstance(),
            defaultTransactionOptions
        )
    }
}