package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.DayInMonth
import com.boryanz.upszakoni.domain.DaysInMonthDataGenerator

class FakeGenerateDaysInMonthsUseCase : DaysInMonthDataGenerator {
  override fun invoke(): List<DayInMonth> {
    return emptyList()
  }
}