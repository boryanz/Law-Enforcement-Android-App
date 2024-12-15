package com.boryanz.upszakoni.data

import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.model.DashboardItem
import com.boryanz.upszakoni.data.model.Offense
import com.boryanz.upszakoni.data.model.TitleItem

enum class DashboardItemDestination {
    laws,
    offenses,
    crimes,
    authorities,
    wanted_criminals,
    writing_guide
}

val goldenQuestions = listOf(
    TitleItem("Што се случило на настанот?"),
    TitleItem("Каде се случил настанот (адреса)?"),
    TitleItem("Кога се случил настанот (датум и време)?"),
    TitleItem("Како се случил настанот?"),
    TitleItem("Зошто се случил настанот?"),
    TitleItem("Со кого се случил настанот (учесници)?"),
    TitleItem("Кој е сторител?"),
)

val dashboardItems = listOf(
    DashboardItem(
        id = DashboardItemDestination.laws,
        title = "Сите закони",
        drawableRes = R.drawable.zakonishta
    ),
    DashboardItem(
        id = DashboardItemDestination.offenses,
        title = "Најчести прекршоци",
        drawableRes = R.drawable.offenses
    ),
    DashboardItem(
        id = DashboardItemDestination.crimes,
        title = "Најчести кривични дела",
        drawableRes = R.drawable.kriminal
    ),
    DashboardItem(
        id = DashboardItemDestination.writing_guide,
        title = "Водич за службени белешки",
        drawableRes = R.drawable.question_solid
    ),
    DashboardItem(
        id = DashboardItemDestination.authorities,
        title = "Полицски овластувања",
        drawableRes = R.drawable.police
    ),
    DashboardItem(
        id = DashboardItemDestination.wanted_criminals,
        title = "Лица по кои се трага",
        drawableRes = R.drawable.prekrsok
    ),
)

val offensesItems = listOf(
    Offense(lawName = "ЗППЈРМ.pdf", title = "Тепачка", description = "чл. 13 од ЗППЈРМ", pagesToLoad = listOf(2)),
    Offense(lawName = "ЗППЈРМ.pdf", title = "Физички напад", description = "чл. 14 од ЗППЈРМ", pagesToLoad = listOf(2)),
    Offense(
        lawName = "ЗППЈРМ.pdf",
        title = "Малтретирање друг на јавно место",
        description = "чл. 10 став 1 од ЗППЈРМ",
        pagesToLoad = listOf(1,2),
    ),
    Offense(
        lawName = "ЗППЈРМ.pdf",
        title = "Карање, викање или непристојно дрско однесување",
        description = "чл. 4 од ЗППЈРМ",
        pagesToLoad = listOf(1)
    ),
    Offense(
        lawName = "ЗППЈРМ.pdf",
        title = "Омаловажување на полициски службеник",
        description = "чл. 13 став 2 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "ЗППЈРМ.pdf",
        title = "Оддвање на уживање на наркотични дроги",
        description = "чл. 20 од ЗППЈРМ",
        pagesToLoad = listOf(3)
    ),
)

val crimesItems = listOf(
    Offense(lawName = "Кривичен законик.pdf", title = "Кражба", description = "чл. 235 од КЗ", pagesToLoad = listOf(105)),
    Offense(lawName = "Кривичен законик.pdf",title = "Тешка кражба", description = "чл. 236 од КЗ", pagesToLoad = listOf(105,106)),
    Offense(lawName = "Кривичен законик.pdf",title = "Разбојништво", description = "чл. 237 од КЗ", pagesToLoad = listOf(106)),
    Offense(lawName = "Кривичен законик.pdf",title = "Разбојничка кражба", description = "чл. 238 од КЗ", pagesToLoad = listOf(106, 107)),
    Offense(lawName = "Кривичен законик.pdf",title = "Предизвикување општа опасност", description = "чл. 288 од КЗ", pagesToLoad = listOf(135)),
    Offense(lawName = "Кривичен законик.pdf",title = "Одземање на моторно возило", description = "чл. 243 од КЗ", pagesToLoad = listOf(108)),
    Offense(lawName = "Кривичен законик.pdf",title = "Поттикнување на кривично дело", description = "чл. 22 од КЗ", pagesToLoad = listOf(5)),
)

val policeAuthorities = listOf(
    TitleItem(title = "Проверка и утврдување на идентитет"),
    TitleItem(title = "Собирање на информации"),
    TitleItem(title = "Повикување"),
    TitleItem(title = "Лишување од слобода"),
    TitleItem(title = "Приведување"),
    TitleItem(title = "Задржување"),
    TitleItem(title = "Потрага по лица и предмети"),
)
