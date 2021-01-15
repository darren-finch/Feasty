package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

abstract class BaseFragment : Fragment() {
    protected val fragmentCompositionRoot by lazy {
        FragmentCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}