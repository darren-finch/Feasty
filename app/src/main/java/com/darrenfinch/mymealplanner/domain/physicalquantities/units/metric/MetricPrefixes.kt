package com.darrenfinch.physicalquantities.units.metric

abstract class MetricPrefix {
    abstract fun getMultiplier(): Double
    abstract override fun toString(): String
    override fun equals(other: Any?): Boolean {
        return if(other !is MetricPrefix) {
            false
        }
        else {
            getMultiplier() == other.getMultiplier()
        }
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class NullPrefix : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 1.0
    }

    override fun toString(): String {
        return ""
    }
}

class Kilo : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 1000.0
    }

    override fun toString(): String {
        return "kilo"
    }
}

class Hecto : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 100.0
    }

    override fun toString(): String {
        return "hecto"
    }
}

class Deca : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 10.0
    }

    override fun toString(): String {
        return "deca"
    }
}

class Deci : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.1
    }

    override fun toString(): String {
        return "deci"
    }
}

class Centi : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.01
    }

    override fun toString(): String {
        return "centi"
    }
}

class Milli : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.001
    }

    override fun toString(): String {
        return "milli"
    }
}