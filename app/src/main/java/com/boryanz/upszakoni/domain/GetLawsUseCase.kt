package com.boryanz.upszakoni.domain

import android.content.Context

class GetLawsUseCase {

    operator fun invoke(context: Context): List<String> {
        return context.assets.list("")?.mapNotNull { it }.orEmpty()
            .filter { it.contains(".pdf") }
    }
}