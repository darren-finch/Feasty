package com.darrenfinch.mymealplanner.domain.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.controllers.BaseActivity

class MainActivity : BaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
