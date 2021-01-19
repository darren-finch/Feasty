package com.darrenfinch.mymealplanner.common.controllers

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.ControllerCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

abstract class BaseDialog : DialogFragment() {

    val controllerCompositionRoot: ControllerCompositionRoot by lazy {
        ControllerCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}