package com.boryanz.upszakoni.data

import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.model.Law
import com.boryanz.upszakoni.data.model.Offense

enum class DashboardItemDestination {
    laws,
    offenses,
    crimes,
    authorities
}

val dashboardItems = listOf(
    Law(id = DashboardItemDestination.laws, title = "Сите закони", drawableRes = R.drawable.zakonishta),
    Law(id = DashboardItemDestination.offenses, title = "Најчести прекршоци", drawableRes = R.drawable.prekrsok),
    Law(id = DashboardItemDestination.crimes, title = "Најчести кривични дела", drawableRes = R.drawable.kriminal),
    Law(id = DashboardItemDestination.authorities, title = "Полицски овластувања", drawableRes = R.drawable.police),
)

val  offensesItems = listOf(
    Offense(title = "Тепачка", description = "чл. 13 од ЗППЈРМ"),
    Offense(title = "Физички напад", description = "чл. 14 од ЗППЈРМ"),
    Offense(title = "Малтретирање друг на јавно место", description = "чл. 10 став 1 од ЗППЈРМ"),
    Offense(title = "Карање, викање или непристојно дрско однесување", description = "чл. 4 од ЗППЈРМ"),
    Offense(title = "Омаловажување на полициски службеник", description = "чл. 13 став 2 од ЗППЈРМ"),
    Offense(title = "Оддвање на уживање на наркотични дроги", description = "чл. 20 од ЗППЈРМ"),
)

