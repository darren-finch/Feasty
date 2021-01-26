package com.darrenfinch.mymealplanner.common.ui.viewmodels

import java.io.Serializable

class StatefulVmListProperty<T : Serializable>(private val vm: StatefulVm) : Serializable {
    private val values = mutableListOf<T>()

    fun set(values: List<T>) {
        this.values.clear()
        this.values.addAll(values)
        vm.onPropertyChanged()
    }
    fun get() = values.toMutableList()

    fun add(value: T) {
        values.add(value)
        vm.onPropertyChanged()
    }
    fun remove(value: T) {
        values.remove(value)
        vm.onPropertyChanged()
    }
}