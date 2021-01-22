package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.DialogFragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.ControllerCompositionRoot
import com.darrenfinch.mymealplanner.common.MainActivity

abstract class BaseDialog : DialogFragment() {
    protected val controllerCompositionRoot: ControllerCompositionRoot by lazy {
        ControllerCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}