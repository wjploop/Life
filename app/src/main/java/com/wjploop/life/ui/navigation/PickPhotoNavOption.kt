package com.wjploop.life.ui.navigation

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.wjploop.life.R

object PickPhotoNavOption {
    val select = navOptions {
        anim {
            enter = R.anim.fragment_fade_enter
            exit = R.anim.fragment_fade_exit
            popEnter = R.anim.fragment_fade_enter
            popExit = R.anim.fragment_fade_exit
        }

    }

}