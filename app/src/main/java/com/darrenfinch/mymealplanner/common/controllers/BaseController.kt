package com.darrenfinch.mymealplanner.common.controllers

import android.os.Bundle

interface BaseController {
    fun setState(state: Bundle?)
    fun getState(): Bundle
}