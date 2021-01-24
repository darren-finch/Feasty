package com.darrenfinch.mymealplanner.screens.foodform.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentFoodFormBinding
import com.darrenfinch.mymealplanner.physicalquantities.*
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementType
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients

class FoodFormViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<FoodFormViewMvc.Listener>(), FoodFormViewMvc {

    private var selectedMeasurementUnit: MeasurementUnit = MeasurementUnit.defaultUnit
    private var selectedMeasurementType: MeasurementType = MeasurementType.defaultType
    private val binding: FragmentFoodFormBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_food_form,
        parent,
        false
    )

    init {
        setRootView(binding.root)
        setupUI()
    }

    override fun bindFoodDetails(foodDetails: UiFood) {
        binding.food = foodDetails
        setSelectedMeasurementType(foodDetails.servingSize.unit.getMeasurementType())
        setSelectedMeasurementUnit(foodDetails.servingSize.unit)
    }

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onDoneSelected() }
        }
        setupMeasurementTypeSpinner()
        setupMeasurementUnitSpinner()
    }

    private fun setupMeasurementUnitSpinner() {
        setupMeasurementUnitSpinnerOnItemSelectedListener()

        val measurementUnitStringList = getValidMeasurementUnitsAsStrings()
        val measurementUnitListArrayAdapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            measurementUnitStringList
        )
        measurementUnitListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.measurementUnitSpinner.adapter = measurementUnitListArrayAdapter
        val measurementUnitSpinnerSelection = getMeasurementUnitSpinnerSelection()
        binding.measurementUnitSpinner.setSelection(measurementUnitSpinnerSelection)
    }

    private fun setupMeasurementTypeSpinner() {
        setupMeasurementTypeSpinnerOnItemSelectedListener()

        val measurementTypeStringList = getValidMeasurementTypesAsStrings()
        val measurementTypeListArrayAdapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            measurementTypeStringList
        )
        measurementTypeListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.measurementTypeSpinner.adapter = measurementTypeListArrayAdapter
        val measurementTypeSpinnerSelection = getMeasurementTypeSpinnerSelection()
        binding.measurementTypeSpinner.setSelection(measurementTypeSpinnerSelection)
    }

    private fun getMeasurementUnitSpinnerSelection(): Int {
        return if(selectedMeasurementType == MeasurementType.Volume) {
            val selectedMeasurementUnitString: String? = volumeUnitsToStrings[selectedMeasurementUnit]
            stringsToVolumeUnits.keys.toList().indexOf(selectedMeasurementUnitString)
        }
        else {
            val selectedMeasurementUnitString: String? = massUnitsToStrings[selectedMeasurementUnit]
            stringsToMassUnits.keys.toList().indexOf(selectedMeasurementUnitString)
        }
    }


    private fun getValidMeasurementUnitsAsStrings(): List<String> {
        return if(selectedMeasurementType == MeasurementType.Volume) {
            stringsToVolumeUnits.keys.toList()
        }
        else {
            stringsToMassUnits.keys.toList()
        }
    }

    private fun setSelectedMeasurementUnit(measurementUnit: MeasurementUnit) {
        selectedMeasurementUnit = measurementUnit
        binding.measurementUnitSpinner.setSelection(getMeasurementUnitSpinnerSelection())
    }

    private fun setSelectedMeasurementType(measurementType: MeasurementType) {
        selectedMeasurementType = measurementType
        binding.measurementTypeSpinner.setSelection(getMeasurementTypeSpinnerSelection())

        // We have to refresh the measurement unit spinner when we change the selected measurement type.
        setupMeasurementUnitSpinner()
    }

    private fun getMeasurementTypeSpinnerSelection(): Int {
        val selectedMeasurementTypeString = MeasurementType.validMeasurementTypesToStrings[selectedMeasurementType]
        return MeasurementType.stringsToValidMeasurementTypes.keys.toList().indexOf(selectedMeasurementTypeString)
    }

    private fun getValidMeasurementTypesAsStrings(): List<String> {
        return MeasurementType.stringsToValidMeasurementTypes.keys.toList()
    }

    private fun setupMeasurementUnitSpinnerOnItemSelectedListener() {
        binding.measurementUnitSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //This will fail if the array of strings given to the parent is incorrect.
                    selectedMeasurementUnit =
                        if(selectedMeasurementType == MeasurementType.Volume)
                            stringsToVolumeUnits[getValidMeasurementUnitsAsStrings()[position]]!!
                        else
                            stringsToMassUnits[getValidMeasurementUnitsAsStrings()[position]]!!
                }
            }
    }

    private fun setupMeasurementTypeSpinnerOnItemSelectedListener() {
        binding.measurementTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //This will fail if the array of strings given to the parent is incorrect.
                    selectedMeasurementType = MeasurementType.stringsToValidMeasurementTypes[getValidMeasurementTypesAsStrings()[position]]!!
                    setupMeasurementUnitSpinner()
                }
            }
    }

    private fun onDoneSelected() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onDoneButtonClicked(getFoodDetails())
        }
    }

    override fun getFoodDetails() = UiFood(
        id = getFoodId(),
        title = getFoodName(),
        macroNutrients = UiMacroNutrients(
            calories = getCalories(),
            carbs = getCarbohydrates(),
            fats = getFat(),
            proteins = getProtein()
        ),
        servingSize = getServingSize()
    )

    private fun getFoodId() = binding.food?.id ?: 0
    private fun getFoodName() = binding.foodNameEditText.text.toString()
    private fun getCalories() = binding.caloriesEditText.text.toString().toInt()
    private fun getCarbohydrates() = binding.carbohydratesEditText.text.toString().toInt()
    private fun getFat() = binding.fatsEditText.text.toString().toInt()
    private fun getProtein() = binding.proteinsEditText.text.toString().toInt()
    private fun getServingSize() = PhysicalQuantity(binding.foodQuantityEditText.text.toString().toDouble(), selectedMeasurementUnit)

    override fun showProgressIndication() {
        binding.formInputsGroup.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.formInputsGroup.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}