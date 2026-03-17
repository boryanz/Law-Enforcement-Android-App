package com.boryanz.upszakoni.utils

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.SharedFlow


val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    fadeIn(
        animationSpec = tween(durationMillis = 300),
        initialAlpha = 0.99f
    )
}

val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    fadeOut(
        animationSpec = tween(durationMillis = 300),
        targetAlpha = 0.99f
    )
}

@SuppressLint("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectEvents(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: (T) -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentOnEvent by rememberUpdatedState(onEvent)
    LaunchedEffect(this, lifecycle, minActiveState) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            this@collectEvents.collect { currentOnEvent(it) }
        }
    }
}