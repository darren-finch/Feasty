package com.darrenfinch.mymealplanner.common.controllers

import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState

interface BaseController {
    // TODO: Pull up other controller methods to this interface

    fun restoreState(state: ControllerSavedState)
    fun getState(): ControllerSavedState
}