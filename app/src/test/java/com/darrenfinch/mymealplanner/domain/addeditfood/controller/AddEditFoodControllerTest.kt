package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.darrenfinch.mymealplanner.Constants
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.domain.usecases.GetSingleFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateFoodUseCase
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class AddEditFoodControllerTest {
    private lateinit var observableFood: ObservableFood

    private val defaultFoodData = TestData.defaultFood
    private val defaultFoodLiveData = TestData.defaultFoodLiveData

    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getSingleFoodUseCase = mockk<GetSingleFoodUseCase>(relaxUnitFun = true)
    private val insertFoodUseCase = mockk<InsertFoodUseCase>(relaxUnitFun = true)
    private val updateFoodUseCase = mockk<UpdateFoodUseCase>(relaxUnitFun = true)
    private val viewModel = mockk<AddEditFoodViewModel>(relaxUnitFun = true)

    private val viewMvc = mockk<AddEditFoodViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AddEditFoodController

    @BeforeEach
    fun setUp() {
        SUT = AddEditFoodController(
            screensNavigator,
            getSingleFoodUseCase,
            insertFoodUseCase,
            updateFoodUseCase,
            viewModel
        )
        SUT.bindView(viewMvc)

        setupViewModelDefaults()
        setupInstantLifecycleEventComponents()
        makeSingleFoodUseCaseReturnDefaultData()
    }

    @Test
    internal fun `onStart() subscribes to viewMvc`() {
        SUT.onStart()
        verify { viewMvc.registerListener(SUT) }
    }

    @Test
    internal fun `onStop() un-subscribes to viewMvc`() {
        SUT.onStop()
        verify { viewMvc.unregisterListener(SUT) }
    }

    @Test
    internal fun `fetchFoodDetailsIfPossibleRebindToViewMvcOtherwise() binds data to viewMvc and doesn't try to fetch data if viewModel is dirty`() {
        makeViewModelDirty()
        every { viewModel.insertingFood } returns false
        SUT.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)
        verify { viewMvc.bindFoodDetails(observableFood) }
        verify { getSingleFoodUseCase.fetchFood(Constants.DEFAULT_FOOD_ID) wasNot called }
    }

    @Test
    internal fun `fetchFoodDetailsIfPossibleRebindToViewMvcOtherwise() fetches data from use case if viewModel is not dirty`() {
        makeViewModelDirty()
        every { viewModel.insertingFood } returns false
        SUT.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)
        verify { getSingleFoodUseCase.fetchFood(Constants.DEFAULT_FOOD_ID) }
    }

    @Test
    internal fun `fetchFoodDetailsIfPossibleRebindToViewMvcOtherwise() updates viewModel data and binds data to viewMvc from use case if viewModel is not dirty`() {
        makeViewModelDirty()
        every { viewModel.insertingFood } returns false
        SUT.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)
        verify { viewModel.setObservableFoodData(defaultFoodData) }
        verify { viewMvc.bindFoodDetails(observableFood) }
    }

    @Test
    internal fun `onDoneButtonClicked() inserts data if inserting data`() {
        SUT.onDoneButtonClicked(defaultFoodData)
        verify { insertFoodUseCase.insertFood(defaultFoodData) }
    }

    @Test
    internal fun `onDoneButtonClicked() updates data if updating data`() {
        every { viewModel.insertingFood } returns false
        SUT.onDoneButtonClicked(defaultFoodData)
        verify { updateFoodUseCase.updateFood(defaultFoodData) }
    }

    @Test
    internal fun `onDoneButtonClicked() navigates to all foods screen`() {
        SUT.onDoneButtonClicked(defaultFoodData)
        verify { screensNavigator.navigateToAllFoodsScreen() }
    }

    private fun makeViewModelDirty() {
        every { viewModel.isNotDirty() } returns true
    }

    private fun makeSingleFoodUseCaseReturnDefaultData() {
        every { getSingleFoodUseCase.fetchFood(Constants.DEFAULT_FOOD_ID) } returns defaultFoodLiveData
    }

    private fun setupViewModelDefaults() {
        observableFood = ObservableFood()
        every { viewModel.getObservableFood() } returns observableFood
        every { viewModel.foodId } returns Constants.DEFAULT_FOOD_ID
        every { viewModel.insertingFood } returns true
    }

    private fun setupInstantLifecycleEventComponents() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        every { viewLifecycleOwner.lifecycle } returns lifecycle
    }
}