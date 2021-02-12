package com.darrenfinch.mymealplanner.common.navigation

import com.ncapdevi.fragnav.FragNavTransactionOptions

object NavigationOptions {
    const val enterAnim = android.R.anim.fade_in
    const val exitAnim = android.R.anim.fade_out

    val defaultTransactionOptions = FragNavTransactionOptions.newBuilder()
        .customAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
        .build()
}