package com.boryanz.upszakoni.data

import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.model.Category
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
        title = "Закони",
        drawableRes = R.drawable.zakonishta
    ),
    DashboardItem(
        id = DashboardItemDestination.offenses,
        title = "Чести прекршоци",
        drawableRes = R.drawable.offenses
    ),
    DashboardItem(
        id = DashboardItemDestination.crimes,
        title = "Чести кривични дела",
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
        title = "Потраги и исчезнати лица",
        drawableRes = R.drawable.prekrsok
    ),
)

val offensesItems = listOf(
    Offense(
        lawName = "Закон за полиција.pdf",
        title = "Лице кое со своето однесување предизвикува сомнение дека е сторител на прекршок или К.Д",
        description = "чл. 38 ст. 1 т.7 од Закон за полиција",
        pagesToLoad = listOf(10)
    ),
    Offense(
        lawName = "Закон за лична карта.pdf",
        title = "Не носи лична карта или одбива да покаже",
        description = "чл. 15 ст. 2 Закон за лична карта",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Тепачка",
        description = "чл. 13 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Учество и потикнување на тепачка",
        description = "чл. 11 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Физички напад",
        description = "чл. 12 ст. 1 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Непочитување наредба на УПС",
        description = "чл. 5 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Фрлање или кршење шишиња, чаши и други предмети",
        description = "чл. 5 од ЗППЈРМ",
        pagesToLoad = listOf(1)
    ),
    Offense(
        lawName = "Закон за јавна чистота.pdf",
        title = "Фрлање и оставање хартија, отпушоци, мастики и др.",
        description = "чл. 14 т. 2 од ЗЈЧ",
        pagesToLoad = listOf(4)
    ),
    Offense(
        lawName = "Закон за јавна чистота.pdf",
        title = "Продажба и излагање на земјоделски и индустриски производи",
        description = "чл. 14 т. 10 од ЗЈЧ",
        pagesToLoad = listOf(4)
    ),
    Offense(
        lawName = "Закон за јавна чистота.pdf",
        title = "Вршење на физиолошки потреби",
        description = "чл. 14 т. 19 од ЗЈЧ",
        pagesToLoad = listOf(4, 5)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Малтретирање друг на јавно место",
        description = "чл. 10 став 1 од ЗППЈРМ",
        pagesToLoad = listOf(1, 2),
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Карање, викање или непристојно дрско однесување",
        description = "чл. 4 од ЗППЈРМ",
        pagesToLoad = listOf(1)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Омаловажување на полициски службеник",
        description = "чл. 13 став 2 од ЗППЈРМ",
        pagesToLoad = listOf(2)
    ),
    Offense(
        lawName = "Закон за прекршоци против јрм.pdf",
        title = "Оддвање на уживање на наркотични дроги",
        description = "чл. 20 од ЗППЈРМ",
        pagesToLoad = listOf(3)
    ),
).groupBy { it.lawName.replace(".pdf", "").trim() }.toSortedMap().map {
    Category(
        name = it.key,
        items = it.value
    )
}

val crimesItems = listOf(
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Кражба",
        description = "чл. 235 од КЗ",
        pagesToLoad = listOf(105)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Тешка кражба",
        description = "чл. 236 од КЗ",
        pagesToLoad = listOf(105, 106)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Разбојништво",
        description = "чл. 237 од КЗ",
        pagesToLoad = listOf(106)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Разбојничка кражба",
        description = "чл. 238 од КЗ",
        pagesToLoad = listOf(106, 107)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Предизвикување општа опасност",
        description = "чл. 288 од КЗ",
        pagesToLoad = listOf(135)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Одземање на моторно возило",
        description = "чл. 243 од КЗ",
        pagesToLoad = listOf(108)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Поттикнување на кривично дело",
        description = "чл. 22 од КЗ",
        pagesToLoad = listOf(5)
    ),
).groupBy { it.lawName.replace(".pdf", "").trim() }.toSortedMap().map {
    Category(
        name = it.key,
        items = it.value
    )
}

val policeAuthorities = listOf(
    TitleItem(title = "Проверка и утврдување на идентитет"),
    TitleItem(title = "Собирање на информации"),
    TitleItem(title = "Повикување"),
    TitleItem(title = "Лишување од слобода"),
    TitleItem(title = "Приведување"),
    TitleItem(title = "Задржување"),
    TitleItem(title = "Потрага по лица и предмети"),
)
