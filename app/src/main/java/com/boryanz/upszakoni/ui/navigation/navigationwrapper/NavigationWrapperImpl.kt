package com.boryanz.upszakoni.ui.navigation.navigationwrapper

import androidx.navigation.NavHostController
import java.lang.ref.WeakReference

class NavigationWrapperImpl(
    navHostController: NavHostController,
): NavigationWrapper {

    private val navHostWeakReference = WeakReference(navHostController)

    override fun navigateNext(destination: Any) {
        navHostWeakReference.get()?.navigate(destination)
    }

    override fun navigateBack() {
        navHostWeakReference.get()?.navigateUp()
    }
}