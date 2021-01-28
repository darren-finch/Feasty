package com.darrenfinch.mymealplanner.testrules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class CoroutinesTestExtension : BeforeEachCallback, AfterAllCallback {
    val testDispatcher = TestCoroutineDispatcher()

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}