package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("RemoveRedundantQualifierName")
@ExperimentalCoroutinesApi
internal class MealPlanControllerTest {
    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<MealPlanVm>()
    private val getMealPlanUseCase = mockk<GetMealPlanUseCase>()
    private val getAllMealPlansUseCase = mockk<GetAllMealPlansUseCase>()
    private val getMealsForMealPlanUseCase = mockk<GetMealsForMealPlanUseCase>()
    private val deleteMealPlanUseCase = mockk<DeleteMealPlanUseCase>()
    private val insertMealPlanMealUseCase = mockk<InsertMealPlanMealUseCase>()
    private val deleteMealPlanMealUseCase = mockk<DeleteMealPlanMealUseCase>()
    private val screensNavigator = mockk<ScreensNavigator>()
    private val toastsHelper = mockk<ToastsHelper>()
    private val dialogsManager = mockk<DialogsManager>()
    private val dialogsEventBus = mockk<DialogsEventBus>()
    private val sharedPreferencesHelper = mockk<SharedPreferencesHelper>()

    val viewMvc = mockk<MealPlanViewMvc>()

    private lateinit var SUT: MealPlanController

    @BeforeEach
    fun setUp() {
        SUT = MealPlanController(
            viewModel,
            getMealPlanUseCase,
            getAllMealPlansUseCase,
            getMealsForMealPlanUseCase,
            insertMealPlanMealUseCase,
            deleteMealPlanUseCase,
            deleteMealPlanMealUseCase,
            screensNavigator,
            toastsHelper,
            dialogsManager,
            dialogsEventBus,
            sharedPreferencesHelper,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )


    }
}