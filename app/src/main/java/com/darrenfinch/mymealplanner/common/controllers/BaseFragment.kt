package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.di.ControllerCompositionRoot
import com.darrenfinch.mymealplanner.domain.main.MainActivity

open class BaseFragment : Fragment() {
    protected val controllerCompositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).activityCompositionRoot)
    }
}