package com.darrenfinch.mymealplanner.screens.mealplan.reactivetest

import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

//internal class GetAllMealPlansUseCaseReactiveTest {
//    private val repository = mockk<MainRepository>()
//
//    private val SUT = GetAllMealPlansUseCaseReactive(repository)
//
//    @Test
//    internal fun `observeAllMealPlans() - observes repository`() {
//        SUT.observeAllMealPlans()
//
//        verify {
//            repository.getAllMealPlans()
//        }
//    }
//}