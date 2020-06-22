package com.darrenfinch.mymealplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.databinding.FragmentAddEditFoodBinding
import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.room.MeasurementUnit
import com.darrenfinch.mymealplanner.viewmodels.AddEditFoodViewModel

class AddEditFoodFragment : Fragment() {

    private val viewModel: AddEditFoodViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    private val args: AddEditFoodFragmentArgs by navArgs()
    private val insertingFood: Boolean by lazy {
        args.foodId < 0
    }

    private lateinit var binding: FragmentAddEditFoodBinding

    private var selectedSpinnerMeasurementUnit = MeasurementUnit.defaultUnit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_edit_food,
                container,
                false
            )
        ifUpdatingFetchFoodFromViewModel()
        setupUI()
        return binding.root
    }

    private fun ifUpdatingFetchFoodFromViewModel() {
        if (!insertingFood) {
            viewModel.fetchFood(args.foodId).observe(viewLifecycleOwner, Observer {
                bindFood(it)
            })
        }
    }
    private fun bindFood(newFood: Food) {
        binding.food = newFood
        setSelectedSpinnerMeasurementUnit(newFood.servingSizeUnit)
    }

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onPositiveButtonSelected() }
        }
        setupSpinnerOnItemSelectedListener()
        setupSpinnerValues()
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
                        MeasurementUnit.fromString(parent?.getItemAtPosition(position).toString())
                }
            }
    }

    private fun setSelectedSpinnerMeasurementUnit(measurementUnit: MeasurementUnit) {
        selectedSpinnerMeasurementUnit = measurementUnit
        binding.foodUnitSpinner.setSelection(selectedSpinnerMeasurementUnit.ordinal)
    }

    private fun setupSpinnerValues() {
        val measurementUnitStringList = getAllMeasurementUnitsAsStrings()
        val measurementUnitListArrayAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                measurementUnitStringList
            )
        }
        measurementUnitListArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.foodUnitSpinner.adapter = measurementUnitListArrayAdapter
        binding.foodUnitSpinner.setSelection(selectedSpinnerMeasurementUnit.ordinal)
    }

    private fun getAllMeasurementUnitsAsStrings() =
        MeasurementUnit.measurementUnitsToStringValues.keys.toList().map { it.toString() }

    private fun onPositiveButtonSelected() {
        val newFood = Food(
            id = getFoodId(),
            title = getFoodName(),
            calories = getCalories(),
            carbohydrates = getCarbohydrates(),
            fat = getFat(),
            protein = getProtein(),
            servingSize = getFoodQuantity(),
            servingSizeUnit = getMeasurementUnit()
        )
        saveFood(newFood)
        navigateToAllFoodsFragment()
    }

    private fun getFoodId() = if (insertingFood) 0 else args.foodId
    private fun getFoodName() = binding.foodNameEditText.text.toString()
    private fun getCalories() = binding.caloriesEditText.text.toString().toInt()
    private fun getCarbohydrates() = binding.carbohydratesEditText.text.toString().toInt()
    private fun getFat() = binding.fatsEditText.text.toString().toInt()
    private fun getProtein() = binding.proteinEditText.text.toString().toInt()
    private fun getFoodQuantity() = binding.foodQuantityEditText.text.toString().toDouble()
    private fun getMeasurementUnit() = selectedSpinnerMeasurementUnit
    private fun saveFood(newFood: Food) {
        if (insertingFood) viewModel.insertFood(newFood) else viewModel.updateFood(newFood)
    }

    private fun navigateToAllFoodsFragment() {
        findNavController().navigate(R.id.allFoodsFragment)
    }
}
