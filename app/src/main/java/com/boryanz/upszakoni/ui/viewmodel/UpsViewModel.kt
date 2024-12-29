package com.boryanz.upszakoni.ui.viewmodel

import androidx.lifecycle.ViewModel

abstract class UpsViewModel<UiEvent> : ViewModel() {

    abstract fun onUiEvent(event: UiEvent)

}