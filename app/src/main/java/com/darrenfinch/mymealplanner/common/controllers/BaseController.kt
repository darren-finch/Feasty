package com.darrenfinch.mymealplanner.common.controllers

import java.io.Serializable

interface BaseController {
    interface BaseSavedState : Serializable

    fun restoreState(state: BaseSavedState)
    fun getState(): BaseSavedState
}