package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot
import com.darrenfinch.mymealplanner.common.MainActivity

abstract class BaseFragment : Fragment() {
    protected val controllerCompositionRoot by lazy {
        FragmentCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}