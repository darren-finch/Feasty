package com.darrenfinch.physicalquantities

import com.darrenfinch.physicalquantities.units.imperial.*
import com.darrenfinch.physicalquantities.units.uscustomary.*
import com.darrenfinch.physicalquantities.units.metric.*

/**
 * A lookup table for the ratios between base units of different measurement systems.
 * In PhysicalQuantity's convert() method, this table is used to convert between measurement systems.
 * E.g a US cup is 0.24 liters. Or you could say an imperial inch is 0.0254 meters.
 */
val ratiosBetweenBaseUnitsOfDifferentMeasurementSystems = hashMapOf(
        USInch() to hashMapOf(MeasurementSystem.Metric to 0.0254, MeasurementSystem.Imperial to 1.0),
        USPound() to hashMapOf(MeasurementSystem.Metric to 453.59237, MeasurementSystem.Imperial to 1.0),
        USCup() to hashMapOf(MeasurementSystem.Metric to 0.24, MeasurementSystem.Imperial to 0.4163544008660172),
        ImperialInch() to hashMapOf(MeasurementSystem.Metric to 0.0254, MeasurementSystem.USCustomary to 1.0),
        ImperialPound() to hashMapOf(MeasurementSystem.Metric to 453.59237, MeasurementSystem.USCustomary to 1.0),
        ImperialPint() to hashMapOf(MeasurementSystem.Metric to 0.56826125, MeasurementSystem.USCustomary to 2.4018),
        Meter() to hashMapOf(MeasurementSystem.USCustomary to 39.3701, MeasurementSystem.Imperial to 39.3701),
        Gram() to hashMapOf(MeasurementSystem.USCustomary to 0.00220462, MeasurementSystem.Imperial to 0.00220462),
        Liter() to hashMapOf(MeasurementSystem.USCustomary to 4.1667, MeasurementSystem.Imperial to 1.75975))