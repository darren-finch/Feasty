package com.darrenfinch.mymealplanner.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

class ExpandableViewGroup(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs)
{
    fun expand()
    {
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val targetHeight: Int = measuredHeight
        layoutParams.height = 1
        visibility = View.VISIBLE

        val expandAnimation: Animation = object : Animation()
        {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?)
            {
                layoutParams.height =
                    if (interpolatedTime == 1f) LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                requestLayout()
            }
            override fun willChangeBounds() = true
        }

        expandAnimation.duration = (targetHeight / getViewDensity()).toLong()
        startAnimation(expandAnimation)
    }
    fun collapse()
    {
        val initialHeight: Int = measuredHeight

        val collapseAnimation: Animation = object : Animation()
        {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?)
            {
                if (interpolatedTime == 1f)
                    visibility = View.GONE
                else
                {
                    layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }
            override fun willChangeBounds() = true
        }

        collapseAnimation.duration = (initialHeight / getViewDensity()).toLong()
        startAnimation(collapseAnimation)
    }

    //This function returns the relative density of the view.
    //Screens with more pixels will make this value larger.
    //See https://developer.android.com/training/multiscreen/screendensities for reference.
    private fun getViewDensity() = context.resources.displayMetrics.density
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}