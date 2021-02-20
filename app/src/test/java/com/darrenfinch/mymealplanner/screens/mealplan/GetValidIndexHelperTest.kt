package com.darrenfinch.mymealplanner.screens.mealplan

import com.darrenfinch.mymealplanner.screens.mealplan.controller.GetValidIndexHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GetValidIndexHelperTest {
    private val SUT = GetValidIndexHelper()

    @Test
    internal fun `getLastValidIndex() - list is empty - throws exception`() {
        assertThrows<IllegalArgumentException> { SUT.getValidIndex(emptyList(), 0) }
    }

    @Test
    internal fun `getLastValidIndex() - index less than 0 - returns last index of list`() {
        val list = listOf(1, 2, 3)
        assertEquals(2, SUT.getValidIndex(list, -1))
    }

    @Test
    internal fun `getLastValidIndex() - index valid - returns index`() {
        val list = listOf(1, 2, 3)
        assertEquals(1, SUT.getValidIndex(list, 1))
    }

    @Test
    internal fun `getLastValidIndex() - index greater than last index of list - returns last index of list`() {
        val list = listOf(1, 2, 3)
        assertEquals(2, SUT.getValidIndex(list, 3))
    }
}