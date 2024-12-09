package com.boryanz.upszakoni.data.model

import androidx.annotation.DrawableRes
import com.boryanz.upszakoni.data.DashboardItemDestination

data class Law(
    val id: DashboardItemDestination,
    val title: String,
    @DrawableRes val drawableRes: Int,
)