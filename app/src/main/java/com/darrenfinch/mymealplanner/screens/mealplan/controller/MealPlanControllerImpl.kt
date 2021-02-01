package com.darrenfinch.mymealplanner.screens.mealplan.controller

import androidx.annotation.StringRes
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
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
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment.Companion.SELECTED_MEAL_PLAN_INDEX
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import kotlin.coroutines.CoroutineContext
import kotlin.math.min

class MealPlanControllerImpl(
    private var viewModel: MealPlanVm,
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

    private sealed class ScreenState : Serializable {
        object Loading : ScreenState()
        class HasData(val mealPlans: List<UiMealPlan>, val mealPlanMeals: List<UiMealPlanMeal>, val mealPlanMacros: MealPlanMacros, val selectedMealPlanIndex: Int) : ScreenState()
        object NoData : ScreenState()
        class Error(@StringRes val errorMsgId: Int) : ScreenState()
    }

    data class SavedState(val viewModel: MealPlanVm) : ControllerSavedState

    private lateinit var viewMvc: MealPlanViewMvc

    private var getAllMealPlansJob: Job? = null
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
        insertMealPlanMealJob?.cancel()
        deleteMealPlanMealJob?.cancel()
    }

    override fun refresh() {
        getAllMealPlansJob?.cancel()
        getAllMealPlansJob = CoroutineScope(backgroundContext).launch {
            withContext(uiContext) {
                setScreenState(ScreenState.Loading)
            }

            val allMealPlans = getAllMealPlansUseCase.getAllMealPlans()
            val selectedMealPlanIndex = min(sharedPreferencesHelper.getInt(SELECTED_MEAL_PLAN_INDEX), allMealPlans.lastIndex)
            val selectedMealPlan = allMealPlans.elementAtOrNull(selectedMealPlanIndex)
                ?: DefaultModels.defaultUiMealPlan.copy(id = Constants.INVALID_ID)

            val mealPlanMeals = getMealsForMealPlanUseCase.getMealsForMealPlan(selectedMealPlan.id)
            val mealPlanMacros =
                MacroCalculator.calculateMealPlanMacros(selectedMealPlan, mealPlanMeals)

            withContext(uiContext) {
                if (allMealPlans.isEmpty())
                    setScreenState(ScreenState.NoData)
                else
                    setScreenState(
                        ScreenState.HasData(
                            allMealPlans,
                            mealPlanMeals,
                            mealPlanMacros,
                            selectedMealPlanIndex
                        )
                    )
            }
        }
    }

    override fun onMealPlanSelected(index: Int) {
        sharedPreferencesHelper.putInt(SELECTED_MEAL_PLAN_INDEX, index)
        viewModel.setSelectedMealPlanIndex(index)
        refresh()
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.toMealPlanFormScreen()
    }

    override fun onDeleteMealPlanClicked() {
        setScreenState(ScreenState.Loading)

        deleteMealPlanJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanUseCase.deleteMealPlan(viewModel.getSelectedMealPlanId())

            withContext(uiContext) {
                viewModel.moveSelectedMealPlanIndex()
                setSelectedMealPlanIndex(viewModel.getSelectedMealPlanIndex())
            }

            refresh()
        }
    }

    override fun onAddNewMealPlanMealClicked() {
        if (viewModel.getSelectedMealPlanId() == Constants.INVALID_ID) {
            toastsHelper.showShortMsg(R.string.no_meal_plan_selected_cant_add_meal)
        } else {
            dialogsManager.showSelectMealPlanMealDialog()
        }
    }

    override fun onDeleteMealPlanMealClicked(mealPlanMealId: Int) {
        deleteMealPlanMealJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanMealUseCase.deleteMealPlanMeal(mealPlanMealId)
            refresh()
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
            refresh()
        }
    }

    private fun setScreenState(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Loading -> {
                viewMvc.showProgressIndication()
                viewMvc.hideEmptyListIndication()
            }
            is ScreenState.NoData -> {
                viewModel.setInitialMealPlans(emptyList())
                viewMvc.bindMealPlans(emptyList())
                viewMvc.bindMealPlanMeals(emptyList())
                viewMvc.bindMealPlanMacros(DefaultModels.defaultMealPlanMacros)
                viewMvc.hideProgressIndication()
                viewMvc.showEmptyListIndication()
            }
            is ScreenState.HasData -> {
                screenState.let {
                    viewMvc.hideProgressIndication()
                    viewMvc.hideEmptyListIndication()
                    viewMvc.bindMealPlans(it.mealPlans)
                    viewModel.setInitialMealPlans(it.mealPlans)
                    if(it.mealPlanMeals.isEmpty())
                        viewMvc.showEmptyListIndication()
                    else
                        viewMvc.bindMealPlanMeals(it.mealPlanMeals)
                    viewMvc.bindMealPlanMacros(it.mealPlanMacros)
                    setSelectedMealPlanIndex(it.selectedMealPlanIndex)
                }
            }
            is ScreenState.Error -> {
                toastsHelper.showShortMsg(screenState.errorMsgId)
            }
        }
    }

    private fun setSelectedMealPlanIndex(index: Int) {
        sharedPreferencesHelper.putInt(SELECTED_MEAL_PLAN_INDEX, index)
        viewModel.setSelectedMealPlanIndex(index)
        viewMvc.setSelectedMealPlanIndexWithoutNotifying(index)
    }
}