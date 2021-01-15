package com.darrenfinch.mymealplanner.common.controllers

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.ControllerCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

abstract class BaseDialog : DialogFragment() {
    interface OnDialogEventListener {
        fun onDismiss(dialogTag: String)
        fun onFinish(dialogTag: String, results: Bundle)
    }

    // It is up to the individual dialogs as to whether they will spit out events or not.
    lateinit var onDialogEventListener: OnDialogEventListener

    val controllerCompositionRoot: ControllerCompositionRoot by lazy {
        ControllerCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}