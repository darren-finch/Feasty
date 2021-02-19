package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.DialogFragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot
import com.darrenfinch.mymealplanner.common.MainActivity

abstract class BaseDialog : DialogFragment() {
    protected val fragmentCompositionRoot: FragmentCompositionRoot by lazy {
        FragmentCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}