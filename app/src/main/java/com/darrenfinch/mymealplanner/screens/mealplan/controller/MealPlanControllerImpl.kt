package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.SelectMealPlanMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment.Companion.SELECTED_MEAL_PLAN_INDEX
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MealPlanControllerImpl(
    private var viewModel: MealPlanVm,
    private val getMealPlanUseCase: GetMealPlanUseCase,
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val toastsHelper: ToastsHelper,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : MealPlanController, MealPlanViewMvc.Listener, DialogsEventBus.Listener {

    data class SavedState(val viewModel: MealPlanVm) : ControllerSavedState

    private lateinit var viewMvc: MealPlanViewMvc

    private var getAllMealPlansJob: Job? = null
    private var getMealsForMealPlanJob: Job? = null
    private var deleteMealPlanJob: Job? = null
    private var insertMealPlanMealJob: Job? = null
    private var deleteMealPlanMealJob: Job? = null

    override fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    override fun onStart() {
        viewMvc.registerListener(this)
        dialogsEventBus.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        getAllMealPlansJob?.cancel()
        deleteMealPlanJob?.cancel()
        deleteMealPlanMealJob?.cancel()
    }

    override fun getAllMealPlans() {
        viewMvc.showProgressIndication()

        getAllMealPlansJob = CoroutineScope(backgroundContext).launch {
            val allMealPlans = getAllMealPlansUseCase.getAllMealPlans()
            viewModel.setInitialMealPlans(allMealPlans)

            withContext(uiContext) {
                viewMvc.bindMealPlans(allMealPlans)
                viewMvc.hideProgressIndication()
                selectUserPreferredMealPlan()
                refreshMealsForSelectedMealPlan()
            }

        }
    }

    private fun selectUserPreferredMealPlan() {
        viewModel.setSelectedMealPlanIndex(sharedPreferencesHelper.getInt(SELECTED_MEAL_PLAN_INDEX))
        viewMvc.setSelectedMealPlanIndexWithoutNotifying(viewModel.getSelectedMealPlanIndex())
    }

    override fun onMealPlanSelected(index: Int) {
        sharedPreferencesHelper.putInt(SELECTED_MEAL_PLAN_INDEX, index)
        viewModel.setSelectedMealPlanIndex(index)
        refreshMealsForSelectedMealPlan()
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.toMealPlanFormScreen()
    }

    override fun onDeleteMealPlanClicked() {
        viewMvc.showProgressIndication()

        deleteMealPlanJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanUseCase.deleteMealPlan(viewModel.getSelectedMealPlanId())
            val allMealPlans = getAllMealPlansUseCase.getAllMealPlans()
            viewModel.setInitialMealPlans(allMealPlans)

            withContext(uiContext) {
                viewModel.moveSelectedMealPlanIndex()
                viewMvc.hideProgressIndication()
                viewMvc.bindMealPlans(allMealPlans)
                viewMvc.setSelectedMealPlanIndexWithoutNotifying(viewModel.getSelectedMealPlanIndex())
            }

            refreshMealsForSelectedMealPlan()
        }
    }

    fun refreshMealsForSelectedMealPlan() {
        if (canRefreshMeals()) {
            getMealsForMealPlanJob = CoroutineScope(backgroundContext).launch {
                withContext(uiContext) {
                    viewMvc.showProgressIndication()
                    viewMvc.hideEmptyListIndication()
                }

                val curMealPlan = getMealPlanUseCase.getMealPlan(viewModel.getSelectedMealPlanId())
                val mealPlanMeals =
                    getMealsForMealPlanUseCase.getMealsForMealPlan(viewModel.getSelectedMealPlanId())

                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    if (mealPlanMeals.isEmpty())
                        viewMvc.showEmptyListIndication()
                    viewMvc.bindMealPlanMeals(mealPlanMeals)

                    calculateMealPlanMacros(curMealPlan, mealPlanMeals)
                }
            }
        }
    }

    private fun calculateMealPlanMacros(mealPlan: UiMealPlan, mealPlanMeals: List<UiMealPlanMeal>) {
        val mealPlanMacros = MacroCalculator.calculateMealPlanMacros(mealPlan, mealPlanMeals)
        viewMvc.bindMealPlanMacros(mealPlanMacros)
    }

    private fun canRefreshMeals() = ((
            getMealsForMealPlanJob == null ||
            getMealsForMealPlanJob?.isCompleted ?: true ||
            getMealsForMealPlanJob?.isCancelled ?: true ||
            getMealsForMealPlanJob?.isActive ?: false))

    override fun onAddNewMealPlanMealClicked() {
        if(viewModel.getSelectedMealPlanId() == Constants.INVALID_ID) {
            toastsHelper.showShortMsg(R.string.no_meal_plan_selected_cant_add_meal)
        } else {
            dialogsManager.showSelectMealPlanMealDialog()
        }
    }

    override fun onDeleteMealPlanMealClicked(mealPlanMealId: Int) {
        deleteMealPlanMealJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanMealUseCase.deleteMealPlanMeal(mealPlanMealId)
            refreshMealsForSelectedMealPlan()
        }
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel)
    }

    override fun onDialogEvent(event: Any, result: DialogResult?) {
        if (event == SelectMealPlanMealDialogEvent.ON_MEAL_SELECTED && result != null) {
            val selectedMeal =
                result.getSerializable(SelectMealPlanMealDialog.SELECTED_MEAL_RESULT) as UiMeal
            val newMealPlanMeal = UiMealPlanMeal(
                Constants.VALID_ID,
                viewModel.getSelectedMealPlanId(),
                selectedMeal.id,
                selectedMeal.title,
                selectedMeal.foods
            )
            addMealToMealPlan(newMealPlanMeal)
        }
    }

    private fun addMealToMealPlan(newMealPlanMeal: UiMealPlanMeal) {
        insertMealPlanMealJob = CoroutineScope(backgroundContext).launch {
            insertMealPlanMealUseCase.insertMealPlanMeal(newMealPlanMeal)
            refreshMealsForSelectedMealPlan()
        }
    }
}