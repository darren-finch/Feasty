package com.darrenfinch.mymealplanner.common.ui.viewmodels

import java.io.Serializable

abstract class StatefulVm : Serializable {
    private var _isDirty = false
    val isDirty: Boolean
        get() = _isDirty

    fun onPropertyChanged() {
        _isDirty = true
    }
}