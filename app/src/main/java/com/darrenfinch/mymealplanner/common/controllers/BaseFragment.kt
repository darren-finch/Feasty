package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.MainActivity
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot

abstract class BaseFragment : Fragment() {
    protected val controllerCompositionRoot by lazy {
        FragmentCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}