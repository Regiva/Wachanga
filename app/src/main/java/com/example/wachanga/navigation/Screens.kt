package com.example.wachanga.navigation

import androidx.fragment.app.FragmentFactory
import com.example.wachanga.feature.main.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    object Main : FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MainFragment()
    }
}
