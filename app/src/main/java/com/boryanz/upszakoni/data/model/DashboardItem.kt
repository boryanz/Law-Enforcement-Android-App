package com.boryanz.upszakoni.data.model

import androidx.annotation.DrawableRes
import com.boryanz.upszakoni.data.NavigationDrawerDestination

data class DashboardItem(
    val id: NavigationDrawerDestination,
    val title: String,
    @DrawableRes val drawableRes: Int,
)