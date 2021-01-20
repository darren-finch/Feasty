package com.darrenfinch.mymealplanner.physicalquantities.units.imperial

import com.darrenfinch.mymealplanner.physicalquantities.MeasurementSystem
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementType
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit

abstract class ImperialUnit : MeasurementUnit() {
    override fun getMeasurementSystem(): MeasurementSystem {
        return MeasurementSystem.Imperial
    }
}

//region Length units
class ImperialInch : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 1.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "in" else "inch ${if (plural) "es" else ""}"
    }
}

class ImperialFoot : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 12.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "ft" else if (plural) "feet" else "foot"
    }
}

class ImperialYard : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 36.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "yd" else "yard${if (plural) "s" else ""}"
    }
}

class ImperialChain : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 792.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "ch" else "chain${if (plural) "s" else ""}"
    }
}

class ImperialFurlong : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 7920.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "fur" else "furlong${if (plural) "s" else ""}"
    }
}

class ImperialMile : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Length
    }

    override fun getBaseUnitRatio(): Double {
        return 63360.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return "mile${if (plural) "s" else ""}"
    }
}
//endregion

//region Mass
class ImperialOunce : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 0.0625
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "oz" else "ounce${if (plural) "es" else ""}"
    }
}

class ImperialPound : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 1.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "lb" else "pound${if (plural) "s" else ""}"
    }
}

class ImperialStone : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 14.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "st" else "stone${if (plural) "s" else ""}"
    }
}

class ImperialQuarter : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 28.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "qtr" else "quarter${if (plural) "s" else ""}"
    }
}

class ImperialHundredweight : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 112.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "cwt" else "hundredweight${if (plural) "s" else ""}"
    }
}

class ImperialTon : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Mass
    }

    override fun getBaseUnitRatio(): Double {
        return 2240.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return "ton${if (plural) "s" else ""}"
    }
}
//endregion

//region Liquid volume units
class ImperialFluidOunce : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getBaseUnitRatio(): Double {
        return 0.05
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "fl oz" else "fluid ounce${if (plural) "s" else ""}"
    }
}

class ImperialGill : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getBaseUnitRatio(): Double {
        return 0.25
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "gi" else "gill${if (plural) "s" else ""}"
    }
}

class ImperialPint : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getBaseUnitRatio(): Double {
        return 1.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "pt" else "pint${if (plural) "s" else ""}"
    }
}

class ImperialQuart : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getBaseUnitRatio(): Double {
        return 2.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "qt" else "quart${if (plural) "s" else ""}"
    }
}

class ImperialGallon : ImperialUnit() {
    override fun getMeasurementType(): MeasurementType {
        return MeasurementType.Volume
    }

    override fun getBaseUnitRatio(): Double {
        return 8.0
    }

    override fun getUnitAsString(plural: Boolean, abbreviated: Boolean): String {
        return if(abbreviated) "gal" else "gallon${if (plural) "s" else ""}"
    }
}
//endregion