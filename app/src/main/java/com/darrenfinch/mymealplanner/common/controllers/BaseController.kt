package com.darrenfinch.mymealplanner.common.controllers

import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState

interface BaseController {
    fun restoreState(state: ControllerSavedState)
    fun getState(): ControllerSavedState
}