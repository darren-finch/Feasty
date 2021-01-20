package com.darrenfinch.mymealplanner.physicalquantities

import com.darrenfinch.mymealplanner.physicalquantities.units.imperial.*
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.*
import com.darrenfinch.mymealplanner.physicalquantities.units.uscustomary.*

/**
 * A lookup table for the ratios between base units of different measurement systems.
 * In PhysicalQuantity's convert() method, this table is used to convert between measurement systems.
 * E.g a US cup is 0.24 liters. Or you could say an imperial inch is 0.0254 meters.
 */
val ratiosBetweenBaseUnitsOfDifferentMeasurementSystems = hashMapOf(
    USInch() to hashMapOf(
        MeasurementSystem.Metric to 0.0254,
        MeasurementSystem.Imperial to 1.0
    ),
    USPound() to hashMapOf(
        MeasurementSystem.Metric to 453.59237,
        MeasurementSystem.Imperial to 1.0
    ),
    USCup() to hashMapOf(
        MeasurementSystem.Metric to 0.24,
        MeasurementSystem.Imperial to 0.4163544008660172
    ),
    ImperialInch() to hashMapOf(
        MeasurementSystem.Metric to 0.0254,
        MeasurementSystem.USCustomary to 1.0
    ),
    ImperialPound() to hashMapOf(
        MeasurementSystem.Metric to 453.59237,
        MeasurementSystem.USCustomary to 1.0
    ),
    ImperialPint() to hashMapOf(
        MeasurementSystem.Metric to 0.56826125,
        MeasurementSystem.USCustomary to 2.4018
    ),
    Meter() to hashMapOf(
        MeasurementSystem.USCustomary to 39.3701,
        MeasurementSystem.Imperial to 39.3701
    ),
    Gram() to hashMapOf(
        MeasurementSystem.USCustomary to 0.00220462,
        MeasurementSystem.Imperial to 0.00220462
    ),
    Liter() to hashMapOf(
        MeasurementSystem.USCustomary to 4.1667,
        MeasurementSystem.Imperial to 1.75975
    )
)

val stringsToMassUnits = hashMapOf(
    Gram(Milli()).toString() to Gram(Milli()),
    Gram(Centi()).toString() to Gram(Centi()),
    Gram(Deci()).toString() to Gram(Deci()),
    Gram().toString() to Gram(),
    Gram(Deca()).toString() to Gram(Deca()),
    Gram(Hecto()).toString() to Gram(Hecto()),
    Gram(Kilo()).toString() to Gram(Kilo()),

    ImperialOunce().toString() to ImperialOunce(),
    ImperialPound().toString() to ImperialPound(),
    ImperialStone().toString() to ImperialStone(),
    ImperialQuarter().toString() to ImperialQuarter(),
    ImperialHundredweight().toString() to ImperialHundredweight(),
    ImperialTon().toString() to ImperialTon(),

    USOunce().toString() to USOunce(),
    USPound().toString() to USPound(),
    USTon().toString() to USTon()
)

val stringsToVolumeUnits = hashMapOf(
    Liter(Milli()).toString() to Liter(Milli()),
    Liter(Centi()).toString() to Liter(Centi()),
    Liter(Deci()).toString() to Liter(Deci()),
    Liter().toString() to Liter(),
    Liter(Deca()).toString() to Liter(Deca()),
    Liter(Hecto()).toString() to Liter(Hecto()),
    Liter(Kilo()).toString() to Liter(Kilo()),

    ImperialFluidOunce().toString() to ImperialFluidOunce(),
    ImperialGill().toString() to ImperialGill(),
    ImperialPint().toString() to ImperialPint(),
    ImperialQuart().toString() to ImperialQuart(),
    ImperialGallon().toString() to ImperialGallon(),

    USTeaspoon().toString() to USTeaspoon(),
    USTablespoon().toString() to USTablespoon(),
    USCup().toString() to USCup(),
    USPint().toString() to USPint(),
    USQuart().toString() to USQuart(),
    USGallon().toString() to USGallon()
)

val massUnitsToStrings = hashMapOf(
    Gram(Milli()) to Gram(Milli()).toString(),
    Gram(Centi()) to Gram(Centi()).toString(),
    Gram(Deci()) to Gram(Deci()).toString(),
    Gram() to Gram().toString(),
    Gram(Deca()) to Gram(Deca()).toString(),
    Gram(Hecto()) to Gram(Hecto()).toString(),
    Gram(Kilo()) to Gram(Kilo()).toString(),

    ImperialOunce() to ImperialOunce().toString(),
    ImperialPound() to ImperialPound().toString(),
    ImperialStone() to ImperialStone().toString(),
    ImperialQuarter() to ImperialQuarter().toString(),
    ImperialHundredweight() to ImperialHundredweight().toString(),
    ImperialTon() to ImperialTon().toString(),

    USOunce() to USOunce().toString(),
    USPound() to USPound().toString(),
    USTon() to USTon().toString()
)

val volumeUnitsToStrings = hashMapOf(
    Liter(Milli()) to Liter(Milli()).toString(),
    Liter(Centi()) to Liter(Centi()).toString(),
    Liter(Deci()) to Liter(Deci()).toString(),
    Liter() to Liter().toString(),
    Liter(Deca()) to Liter(Deca()).toString(),
    Liter(Hecto()) to Liter(Hecto()).toString(),
    Liter(Kilo()) to Liter(Kilo()).toString(),

    ImperialFluidOunce() to ImperialFluidOunce().toString(),
    ImperialGill() to ImperialGill().toString(),
    ImperialPint() to ImperialPint().toString(),
    ImperialQuart() to ImperialQuart().toString(),
    ImperialGallon() to ImperialGallon().toString(),

    USTeaspoon() to USTeaspoon().toString(),
    USTablespoon() to USTablespoon().toString(),
    USCup() to USCup().toString(),
    USPint() to USPint().toString(),
    USQuart() to USQuart().toString(),
    USGallon() to USGallon().toString()
)

val stringsToUnits = hashMapOf(
    Meter(Milli()).toString() to Meter(Milli()),
    Meter(Centi()).toString() to Meter(Centi()),
    Meter(Deci()).toString() to Meter(Deci()),
    Meter().toString() to Meter(),
    Meter(Deca()).toString() to Meter(Deca()),
    Meter(Hecto()).toString() to Meter(Hecto()),
    Meter(Kilo()).toString() to Meter(Kilo()),

    ImperialInch().toString() to ImperialInch(),
    ImperialFoot().toString() to ImperialFoot(),
    ImperialYard().toString() to ImperialYard(),
    ImperialMile().toString() to ImperialMile(),

    USInch().toString() to USInch(),
    USFoot().toString() to USFoot(),
    USYard().toString() to USYard(),
    USMile().toString() to USMile(),

    Gram(Milli()).toString() to Gram(Milli()),
    Gram(Centi()).toString() to Gram(Centi()),
    Gram(Deci()).toString() to Gram(Deci()),
    Gram().toString() to Gram(),
    Gram(Deca()).toString() to Gram(Deca()),
    Gram(Hecto()).toString() to Gram(Hecto()),
    Gram(Kilo()).toString() to Gram(Kilo()),

    ImperialOunce().toString() to ImperialOunce(),
    ImperialPound().toString() to ImperialPound(),
    ImperialStone().toString() to ImperialStone(),
    ImperialQuarter().toString() to ImperialQuarter(),
    ImperialHundredweight().toString() to ImperialHundredweight(),
    ImperialTon().toString() to ImperialTon(),

    USOunce().toString() to USOunce(),
    USPound().toString() to USPound(),
    USTon().toString() to USTon(),

    Liter(Milli()).toString() to Liter(Milli()),
    Liter(Centi()).toString() to Liter(Centi()),
    Liter(Deci()).toString() to Liter(Deci()),
    Liter().toString() to Liter(),
    Liter(Deca()).toString() to Liter(Deca()),
    Liter(Hecto()).toString() to Liter(Hecto()),
    Liter(Kilo()).toString() to Liter(Kilo()),

    ImperialFluidOunce().toString() to ImperialFluidOunce(),
    ImperialGill().toString() to ImperialGill(),
    ImperialPint().toString() to ImperialPint(),
    ImperialQuart().toString() to ImperialQuart(),
    ImperialGallon().toString() to ImperialGallon(),

    USTeaspoon().toString() to USTeaspoon(),
    USTablespoon().toString() to USTablespoon(),
    USCup().toString() to USCup(),
    USPint().toString() to USPint(),
    USQuart().toString() to USQuart(),
    USGallon().toString() to USGallon()
)