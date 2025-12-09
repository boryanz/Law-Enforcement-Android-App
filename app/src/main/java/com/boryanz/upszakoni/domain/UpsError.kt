package com.boryanz.upszakoni.domain

import android.content.Context
import com.boryanz.upszakoni.ui.screens.error.GeneralErrorActivity
import com.boryanz.upszakoni.ui.screens.error.NoConnectionActivity


interface BaseError {
  fun handle(context: Context)
}

sealed interface UpsError : BaseError {
  data object NoInternetConnectionError : UpsError {
    override fun handle(context: Context) {
      context.startActivity(NoConnectionActivity.createIntent(context))
    }
  }

  data object GeneralError : UpsError {
    override fun handle(context: Context) {
      context.startActivity(GeneralErrorActivity.createIntent(context))
    }
  }
}