package com.darrenfinch.mymealplanner.common.controllers

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

abstract class BaseDialog : DialogFragment() {
    interface OnDialogEventListener {
        fun onDismiss(dialogTag: String)
        fun onFinish(dialogTag: String, results: Bundle)
    }

    // It is up to the individual dialogs as to whether they will spit out events or not.
    lateinit var onDialogEventListener: OnDialogEventListener

    val fragmentCompositionRoot: FragmentCompositionRoot by lazy {
        FragmentCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}