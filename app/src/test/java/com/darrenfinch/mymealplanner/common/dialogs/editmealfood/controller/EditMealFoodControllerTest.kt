package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodVm
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class EditMealFoodControllerTest {
    private val defUiMealFood = TestDefModels.defUiMealFood
    private val defUiMacros = TestDefModels.defUiMacros
    private val defIndex = 0

    private val viewModel = mockk<EditMealFoodVm>(relaxUnitFun = true)
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
        )

        SUT.setArgs(defUiMealFood, defIndex)

        SUT.bindView(viewMvc)
    }

    @Test
    internal fun `bindViewStateToView() binds view state to view`() {
        every { viewModel.getMealFoodTitle() } returns defUiMealFood.title
        every { viewModel.getUpdatedMealFoodMacros() } returns defUiMacros
        every { viewModel.getDesiredMealFoodServingSize() } returns defUiMealFood.desiredServingSize

        SUT.bindViewStateToView()

        verifySequence {
            viewMvc.bindMealFoodTitle(defUiMealFood.title)
            viewMvc.bindMealFoodMacros(defUiMealFood.macroNutrients)
            viewMvc.bindMealFoodDesiredServingSize(defUiMealFood.desiredServingSize)
        }
    }

    @Test
    internal fun `onMealFoodServingSizeQuantityChange() sets desired serving size on view model`() {
        val testQuantity = 1.0
        every { viewModel.getUpdatedMealFoodMacros() } returns defUiMacros

        SUT.onMealFoodServingSizeQuantityChange(testQuantity)

        verify { viewModel.setDesiredServingSizeQuantity(testQuantity) }
    }

    @Test
    internal fun `onMealFoodServingSizeQuantityChange() binds updated meal food macros to view`() {
        every { viewModel.getUpdatedMealFoodMacros() } returns defUiMacros

        SUT.onMealFoodServingSizeQuantityChange(0.0) // quantity doesn't matter for this test

        verify { viewMvc.bindMealFoodMacros(defUiMacros) }
    }

    @Test
    internal fun `onPositiveButtonClicked() closes current dialog`() {
        every { viewModel.getMealFoodDetails() } returns defUiMealFood

        SUT.onPositiveButtonClicked()

        verify { dialogsManager.clearDialog() }
    }

    @Test
    internal fun `onPositiveButtonClicked() sends out correct event with result`() {
        every { viewModel.getMealFoodDetails() } returns defUiMealFood

        SUT.onPositiveButtonClicked()

        verify { dialogsEventBus.postEvent(EditMealFoodDialogEvent.OnPositiveButtonClicked(defUiMealFood, defIndex)) }
    }
}