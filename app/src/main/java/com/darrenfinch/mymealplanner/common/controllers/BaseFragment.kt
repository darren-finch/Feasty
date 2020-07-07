package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.darrenfinch.mymealplanner.common.dependencyinjection.AndroidComponentsConfig
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

open class BaseFragment : Fragment() {
    protected val fragmentCompositionRoot by lazy {
        FragmentCompositionRoot(
            AndroidComponentsConfig(findNavController()),
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}