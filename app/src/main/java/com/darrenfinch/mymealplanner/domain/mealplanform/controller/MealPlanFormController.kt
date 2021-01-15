package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormFragment.Companion.MEAL_PLAN_DETAILS
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class MealPlanFormController(
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealPlanFormViewMvc.Listener, BackPressListener {
    private var mealPlanDetails: MealPlan? = null

    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc

        mealPlanDetails?.let {
            viewMvc.bindMealPlanDetails(it)
        }
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
    }

    override fun onDoneButtonClicked(editedMealPlanDetails: MealPlan) {
        insertMealPlanUseCase.insertMealPlan(editedMealPlanDetails)
        screensNavigator.goBack()
    }

    override fun setState(state: Bundle?) {
        mealPlanDetails = state?.getSerializable(MEAL_PLAN_DETAILS) as MealPlan?
    }
    override fun getState(): Bundle {
        return Bundle().apply {
            putSerializable(MEAL_PLAN_DETAILS, viewMvc.getMealPlanDetails())
        }
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}