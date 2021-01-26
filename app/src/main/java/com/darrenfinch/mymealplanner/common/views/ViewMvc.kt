package com.darrenfinch.mymealplanner.common.views

import android.view.View

interface ViewMvc {
    fun getRootView() : View
    fun releaseViewRefs()
}