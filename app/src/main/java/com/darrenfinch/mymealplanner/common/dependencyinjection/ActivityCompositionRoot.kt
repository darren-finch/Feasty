package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.helpers.KeyboardHelper
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.NavigationOptions
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.data.MainRepository
import com.ncapdevi.fragnav.FragNavController

//This composition root is scoped to a particular activity
class ActivityCompositionRoot(
    private val compositionRoot: CompositionRoot,
    private val activity: FragmentActivity
) {
    private val fragNavController =
        FragNavController(activity.supportFragmentManager, R.id.fragmentContainer)
    private val screensNavigator = ScreensNavigator(
        KeyboardHelper(activity, activity.requireViewById(R.id.fragmentContainer)),
        fragNavController,
        NavigationOptions.defaultTransactionOptions
    )
    private val dialogsManager = DialogsManager(getActivity(), fragNavController)
    private val toastsHelper = ToastsHelper(activity)
    private val sharedPreferencesHelper = SharedPreferencesHelper(activity)

    fun getActivity(): FragmentActivity {
        return activity
    }

    fun getApplication(): Application {
        return compositionRoot.getApplication()
    }

    fun getMainRepository(): MainRepository {
        return compositionRoot.getMainRepository()
    }

    fun getDialogsEventBus(): DialogsEventBus {
        return compositionRoot.getDialogsEventBus()
    }

    fun getScreenDataReturnBuffer(): ScreenDataReturnBuffer {
        return compositionRoot.getScreenDataReturnBuffer()
    }

    fun getScreensNavigator(): ScreensNavigator {
        return screensNavigator
    }

    fun getDialogsManager(): DialogsManager {
        return dialogsManager
    }

    fun getToastsHelper(): ToastsHelper {
        return toastsHelper
    }

    fun getSharedPreferencesHelper(): SharedPreferencesHelper {
        return sharedPreferencesHelper
    }
}