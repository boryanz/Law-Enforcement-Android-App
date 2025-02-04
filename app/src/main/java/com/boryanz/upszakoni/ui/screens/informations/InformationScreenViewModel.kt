package com.boryanz.upszakoni.ui.screens.informations

import androidx.lifecycle.ViewModel
import com.boryanz.upszakoni.data.model.TitleItem
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository

class InformationScreenViewModel(remoteConfigRepository: RemoteConfigRepository) :
    ViewModel() {

    val information =
        remoteConfigRepository
            .remoteConfigState
            .value
            .usefulInformations
            .split("<-->")
            .map { TitleItem(it) }
}