package com.boryanz.upszakoni.fakes

import com.boryanz.upszakoni.data.local.database.model.bonussalary.DayInMonth
import com.boryanz.upszakoni.domain.DaysInMonthDataGenerator

class FakeGenerateDaysInMonthsUseCase : DaysInMonthDataGenerator {
  override fun invoke(): List<DayInMonth> {
    return emptyList()
  }
}