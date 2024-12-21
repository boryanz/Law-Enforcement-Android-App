package com.boryanz.upszakoni.data

import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.model.Category
import com.boryanz.upszakoni.data.model.DashboardItem
import com.boryanz.upszakoni.data.model.Offense
import com.boryanz.upszakoni.data.model.TitleItem

enum class NavigationDrawerDestination {
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
        id = NavigationDrawerDestination.laws,
        title = "Закони",
        drawableRes = R.drawable.zakonishta
    ),
    DashboardItem(
        id = NavigationDrawerDestination.offenses,
        title = "Чести прекршоци",
        drawableRes = R.drawable.offenses
    ),
    DashboardItem(
        id = NavigationDrawerDestination.crimes,
        title = "Чести кривични дела",
        drawableRes = R.drawable.kriminal
    ),
    DashboardItem(
        id = NavigationDrawerDestination.writing_guide,
        title = "Водич за службени белешки",
        drawableRes = R.drawable.question_solid
    ),
    DashboardItem(
        id = NavigationDrawerDestination.authorities,
        title = "Полицски овластувања",
        drawableRes = R.drawable.police
    ),
    DashboardItem(
        id = NavigationDrawerDestination.wanted_criminals,
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
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Телесна повреда",
        description = "чл. 130 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Тешка телесна повреда",
        description = "чл. 131 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Учество во тепачка",
        description = "чл. 132 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Загрозување на сигурност",
        description = "чл. 144 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Затајување",
        description = "чл. 239 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Фалсификување исправа",
        description = "чл. 378 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Спречување на службено лице во вршење на службено дејствие",
        description = "чл. 382 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Напад на службено лице при вршење работи на безбедноста",
        description = "чл. 383 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Симнување и оштетување службен печат или знак",
        description = "чл. 389 од КЗ",
        pagesToLoad = listOf(5)
    ),
    Offense(
        lawName = "Кривичен законик.pdf",
        title = "Измама",
        description = "чл. 247 од КЗ",
        pagesToLoad = listOf(5)
    ),
).groupBy { it.lawName.replace(".pdf", "").trim() }.toSortedMap().map {
    Category(
        name = it.key,
        items = it.value
    )
}

val policeAuthorities = listOf(
    TitleItem(title = "Проверка и утврдување на идентитет на лица и предмети"),
    TitleItem(title = "Собирање на информации"),
    TitleItem(title = "Повикување"),
    TitleItem(title = "Лишување од слобода"),
    TitleItem(title = "Приведување"),
    TitleItem(title = "Задржување"),
    TitleItem(title = "Потрага по лица и предмети"),
    TitleItem(title = "Прикриено полициско дејствување"),
    TitleItem(title = "Пренасочување, насочување или ограничување на движењето на лица и превозни средства на определен простор за нужно потребно време"),
    TitleItem(title = "Предупредување и наредување"),
    TitleItem(title = "Преглед односно претрес на определени објекти и простории на државни органи,\n" +
            "институции што вршат јавни овластувања и други правни лица и увид во определена нивна\n" +
            "документација"),
    TitleItem(title = "Влегување во туѓ дом и други затворени простории"),
    TitleItem(title = "Сопирање, преглед односно претрес на лица, багаж и превозни средства"),
    TitleItem(title = "Обезбедување, преглед и увид на местото на настанот"),
    TitleItem(title = "Примање на пријави и поплаки, поднесување на пријави и известувања"),
    TitleItem(title = "Препознавање"),
    TitleItem(title = "Јавно распишување награда"),
    TitleItem(title = "Снимање на јавни места"),
    TitleItem(title = "Полиграфско тестирање"),
    TitleItem(title = "Собирање, обработка, анализирање, користење, оценување, пренесување,\n" +
            "чување и бришење на податоци, како и обработка на лични податоци под услови и на начин\n" +
            "утврдени со овој и посебен закон"),
    TitleItem(title = "Примена на посебни истражни мерки"),
    TitleItem(title = "Заштита на лица опфатени согласно со прописите за заштита на сведоци"),
    TitleItem(title = "Употреба на средства за присилба"),
)
