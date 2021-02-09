package com.darrenfinch.mymealplanner.common.controllers

interface BaseController {
    // TODO: Pull up other controller methods to this interface

    fun restoreState(state: ControllerSavedState)
    fun getState(): ControllerSavedState
}