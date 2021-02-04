package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodVm
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class EditMealFoodControllerTest {
    private val defMealFood = TestDefaultModels.defUiMealFood

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<EditMealFoodVm>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)

    private val viewMvc = mockk<EditMealFoodViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: EditMealFoodController

    @BeforeEach
    fun setUp() {
        SUT = EditMealFoodController(
            viewModel,
            dialogsManager,
            dialogsEventBus,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)


    }

    @Test
    internal fun `getMealFood() shows progress indication, then binds meal food details to view model and view mvc, then hides progress indication`() = runBlockingTest {
        SUT.getMealFood()

        verify {
            viewMvc.showProgressIndication()
            viewModel.bindMealFoodDetails(defMealFood)
            viewMvc.bindMealFoodDetails(defMealFood)
            viewMvc.hideProgressIndication()
        }
    }
}