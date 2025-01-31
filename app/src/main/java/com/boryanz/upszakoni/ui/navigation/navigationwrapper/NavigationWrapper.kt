package com.boryanz.upszakoni.ui.navigation.navigationwrapper

/**
 * Contract for the Navigation wrapper
 */
interface NavigationWrapper {
    fun navigateNext(destination: Any)
    fun navigateBack()
}