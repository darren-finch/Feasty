package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScreensNavigatorTest {
    private val navController = mockk<FragNavController>(relaxUnitFun = true)
    private val fakeBundle = mockk<Bundle>(relaxUnitFun = true)

    private val enterAnim = android.R.anim.fade_in
    private val exitAnim = android.R.anim.fade_out

    private val defTransactionOptions = mockk<FragNavTransactionOptions>()

    private lateinit var SUT: ScreensNavigator

    @BeforeEach
    internal fun setUp() {
        SUT = ScreensNavigator(navController, defTransactionOptions)

        every { defTransactionOptions.enterAnimation } returns enterAnim
        every { defTransactionOptions.exitAnimation } returns exitAnim
        every { defTransactionOptions.popEnterAnimation } returns enterAnim
        every { defTransactionOptions.popExitAnimation } returns exitAnim
        every { navController.popFragment(any()) } returns true
    }

    @Test
    internal fun `init() initializes navController with correct args`() {
        SUT.init(fakeBundle)

        verify { navController.initialize(FragNavController.TAB1, fakeBundle) }
    }

    @Test
    internal fun `onSaveInstanceState() calls onSaveInstanceState on navController`() {
        SUT.onSaveInstanceState(fakeBundle)

        verify { navController.onSaveInstanceState(fakeBundle) }
    }

    @Test
    internal fun `navigateUp() returns false if current fragment is root`() {
        every { navController.isRootFragment } returns true

        assertEquals(false, SUT.navigateUp())
    }

    @Test
    internal fun `navigateUp() doesn't pop top fragment if current fragment is root`() {
        every { navController.isRootFragment } returns true

        SUT.navigateUp()

        verify(inverse = true) { navController.popFragment(any()) }
    }

    @Test
    internal fun `navigateUp() returns true if current fragment is not root`() {
        every { navController.isRootFragment } returns false

        assertEquals(true, SUT.navigateUp())
    }

    @Test
    internal fun `navigateUp() pops top fragment if current fragment is not root`() {
        every { navController.isRootFragment } returns false

        SUT.navigateUp()

        verify { navController.popFragment(any()) }
    }
}