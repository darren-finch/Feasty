package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogData
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
    private val defIndex = 0

    private val screenData = mockk<EditMealFoodDialogData>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)

    private val viewMvc = mockk<EditMealFoodViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: EditMealFoodController

    @BeforeEach
    fun setUp() {
        SUT = EditMealFoodController(
            screenData,
            dialogsManager,
            dialogsEventBus,
        )

        SUT.setArgs(defUiMealFood, defIndex)

        SUT.bindView(viewMvc)
    }

    @Test
    internal fun `bindViewStateToView() binds view state to view`() {
        val macros = TestDefModels.defUiMacros

        every { screenData.getTitle() } returns defUiMealFood.title
        every { screenData.getMacrosBasedOnDesiredServingSize() } returns macros
        every { screenData.getDesiredServingSize() } returns defUiMealFood.desiredServingSize

        SUT.bindViewStateToView()

        verifySequence {
            viewMvc.bindMealFoodTitle(defUiMealFood.title)
            viewMvc.bindMealFoodMacros(macros)
            viewMvc.bindMealFoodDesiredServingSize(defUiMealFood.desiredServingSize)
        }
    }

    @Test
    internal fun `onMealFoodServingSizeQuantityChange() sets desired serving size on screen data`() {
        val testQuantity = 1.0
        every { screenData.getMacrosBasedOnDesiredServingSize() } returns TestDefModels.defUiMacros

        SUT.onMealFoodServingSizeQuantityChange(testQuantity)

        verify { screenData.setDesiredServingSizeQuantity(testQuantity) }
    }

    @Test
    internal fun `onMealFoodServingSizeQuantityChange() binds macros based on desired serving size to view`() {
        val macros = TestDefModels.defUiMacros

        every { screenData.getMacrosBasedOnDesiredServingSize() } returns macros

        SUT.onMealFoodServingSizeQuantityChange(0.0) // quantity doesn't matter for this test

        verify { viewMvc.bindMealFoodMacros(macros) }
    }

    @Test
    internal fun `onPositiveButtonClicked() closes current dialog`() {
        every { screenData.getMealFoodDetails() } returns defUiMealFood

        SUT.onPositiveButtonClicked()

        verify { dialogsManager.clearDialog() }
    }

    @Test
    internal fun `onPositiveButtonClicked() sends out correct event with result`() {
        every { screenData.getMealFoodDetails() } returns defUiMealFood

        SUT.onPositiveButtonClicked()

        verify { dialogsEventBus.postEvent(EditMealFoodDialogEvent.OnPositiveButtonClicked(defUiMealFood, defIndex)) }
    }
}