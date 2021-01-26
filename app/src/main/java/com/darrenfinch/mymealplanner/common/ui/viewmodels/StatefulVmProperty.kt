package com.darrenfinch.mymealplanner.common.ui.viewmodels

import java.io.Serializable

class StatefulVmProperty<T : Serializable>(private var value: T, private val vm: StatefulVm) : Serializable {
    fun setWithoutNotifying(newValue: T) {
        value = newValue
    }
    fun set(newValue: T) {
        value = newValue
        vm.onPropertyChanged()
    }
    fun get() = value
}