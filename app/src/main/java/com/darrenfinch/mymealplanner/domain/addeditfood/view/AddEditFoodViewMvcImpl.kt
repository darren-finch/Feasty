package com.darrenfinch.mymealplanner.domain.addeditfood.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAddEditFoodBinding
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

class AddEditFoodViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    private val insertingFood: Boolean
) : BaseObservableViewMvc<AddEditFoodViewMvc.Listener>(), AddEditFoodViewMvc {

    private var selectedSpinnerMeasurementUnit = MetricUnit.defaultUnit
    private val binding: FragmentAddEditFoodBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_add_edit_food,
        parent,
        false
    )

    init {
        setRootView(binding.root)
    }

    override fun bindFoodDetails(observableFood: ObservableFood) {
        binding.food = observableFood
        setupUI()
        setSelectedSpinnerMeasurementUnit(observableFood.servingSizeUnit)
    }

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onDoneSelected() }
        }
        setupSpinnerOnItemSelectedListener()
        setupSpinnerValues()
    }

    private fun setupSpinnerValues() {
        val measurementUnitStringList = getAllMeasurementUnitsAsStrings()
        val measurementUnitListArrayAdapter = ArrayAdapter(
            getContext(),
            android.R.layout.simple_spinner_item,
            measurementUnitStringList
        )
        measurementUnitListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.foodUnitSpinner.adapter = measurementUnitListArrayAdapter
        binding.foodUnitSpinner.setSelection(selectedSpinnerMeasurementUnit.ordinal)
    }

    private fun getAllMeasurementUnitsAsStrings(): List<String> {
        return MetricUnit.stringValuesToMetricUnits.keys.toList()
    }

    private fun setSelectedSpinnerMeasurementUnit(metricUnit: MetricUnit) {
        selectedSpinnerMeasurementUnit = metricUnit
        binding.foodUnitSpinner.setSelection(selectedSpinnerMeasurementUnit.ordinal)
    }

    private fun setupSpinnerOnItemSelectedListener() {
        binding.foodUnitSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //This will fail if the array of strings given to the parent is incorrect.
                    selectedSpinnerMeasurementUnit =
                        MetricUnit.fromString(parent?.getItemAtPosition(position).toString())
                }
            }
    }

    private fun onDoneSelected() {
        val newFood = Food(
            id = getFoodId(),
            title = getFoodName(),
            macroNutrients = MacroNutrients(
                calories = getCalories(),
                carbs = getCarbohydrates(),
                fat = getFat(),
                protein = getProtein()
            ),
            servingSize = getFoodQuantity(),
            servingSizeUnit = getMeasurementUnit()
        )

        for (listener in getListeners()) {
            listener.onDoneButtonClicked(newFood)
        }
    }

    private fun getFoodId() = if (insertingFood) 0 else binding.food!!.id
    private fun getFoodName() = binding.foodNameEditText.text.toString()
    private fun getCalories() = binding.caloriesEditText.text.toString().toInt()
    private fun getCarbohydrates() = binding.carbohydratesEditText.text.toString().toInt()
    private fun getFat() = binding.fatsEditText.text.toString().toInt()
    private fun getProtein() = binding.proteinEditText.text.toString().toInt()
    private fun getFoodQuantity() = binding.foodQuantityEditText.text.toString().toDouble()
    private fun getMeasurementUnit() = selectedSpinnerMeasurementUnit
}