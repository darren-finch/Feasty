package com.darrenfinch.mymealplanner.common.controllers

interface StatefulController {
    fun restoreState(state: ControllerSavedState)
    fun getState(): ControllerSavedState
}