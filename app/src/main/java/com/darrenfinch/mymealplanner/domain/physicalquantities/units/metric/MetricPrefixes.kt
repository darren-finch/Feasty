package com.darrenfinch.mymealplanner.domain.physicalquantities.units.metric

abstract class MetricPrefix {
    abstract fun getMultiplier(): Double
    abstract override fun toString(): String
    abstract fun getAsString(abbreviated: Boolean = false): String
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

    override fun getAsString(abbreviated: Boolean): String {
        return ""
    }
}

class Kilo : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 1000.0
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "k" else "kilo"
    }
}

class Hecto : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 100.0
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "h" else "hecto"
    }
}

class Deca : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 10.0
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "da" else "deca"
    }
}

class Deci : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.1
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "d" else "deci"
    }
}

class Centi : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.01
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "c" else "centi"
    }
}

class Milli : MetricPrefix() {
    override fun getMultiplier(): Double {
        return 0.001
    }

    override fun toString(): String {
        return getAsString(false)
    }

    override fun getAsString(abbreviated: Boolean): String {
        return if(abbreviated) "m" else "milli"
    }
}