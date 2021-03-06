package com.eky.evimiss.utils

import com.eky.evimiss.data.model.MyDate
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoUnit
import java.util.*
import kotlin.Exception as KotlinException

const val DATE_FORMAT = "yyyy-MM-dd"
const val DATE_TIME_FORMAT = "hh:mm:ss"
const val DETAILED_DATE_FORMAT = "dd MMMM EEEE, yyyy"
const val DETAILED_DATE_TIME_FORMAT = "dd MMMM EEEE, yyyy, hh:mm:ss"
const val TIMELINE_DATE = "MMMM, yyyy"
const val TEST_DATE = "2022-02-09"
const val INITIAL_UFC_DATE = "0000000000" //01.01.1970 02:00:00

fun isToday(givenDate: String): Boolean {
    givenDate.convert2FormattedLocalDate().apply {
       return try {
            isEqual(getToday())
        } catch (e: KotlinException) { //Used cause of ZoneRulesException on Preview
            isEqual(TEST_DATE.convert2FormattedLocalDate())
        }
    }
}

fun LocalDate.isToday(): Boolean {
    return try {
            isEqual(getToday())
        } catch (e: KotlinException) { //Used cause of ZoneRulesException on Preview
            isEqual(TEST_DATE.convert2FormattedLocalDate())
        }
}

fun getMonthList(date: LocalDate = getToday()): MutableList<MyDate> {
    val listOfThisMonthsDate = mutableListOf<MyDate>()
    for (day in 1..date.lengthOfMonth())
        listOfThisMonthsDate.add(
            MyDate(date = LocalDate.of(date.year, date.month, day))
        )
    return listOfThisMonthsDate
}

fun getWeekList(givenDate: LocalDate = getToday()) = getMonthList(givenDate).getWeeksOfMonth()

fun getDayNames(): List<String> { //8
    val today = getToday()
    val dayList = mutableListOf(today)
    val weekList = mutableListOf<Pair<Int, String>>()

    for (dayNo in 1..6)
        dayList.add(today.plusDays(dayNo.toLong()))

    for (day in dayList)
        weekList.add(
            Pair(
                first = day.dayOfWeek.value,
                second = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            )
        )
    weekList.sortBy { it.first }
    return weekList.map { it.second }
}

fun MutableList<MyDate>.getWeeksOfMonth(): MutableList<Pair<Int, List<MyDate>>> {
    val weeks: MutableList<Pair<Int, List<MyDate>>> = mutableListOf()
    val firstDay = this.first()
    val lastDayInMonth = firstDay.date.lengthOfMonth()
    val tempDayList = mutableListOf<MyDate>()

    for (dayNo in 0 until lastDayInMonth)
        tempDayList.add(MyDate(date = firstDay.date.plusDays(dayNo.toLong())))

    if (firstDay.dayOfWeekValue != 1)
        for (dayNo in 1 until firstDay.dayOfWeekValue)
            tempDayList.add(0, MyDate(date = firstDay.date.minusDays(dayNo.toLong())))

    val lastDayOfMonth = tempDayList.last()
    if (lastDayOfMonth.dayOfWeekValue < 7)
        for (dayNo in 1..7-lastDayOfMonth.dayOfWeekValue)
            tempDayList.add(MyDate(date = lastDayOfMonth.date.plusDays(dayNo.toLong())))

    val otherWeeks = tempDayList.chunked(7)

    for (weekNo in 1..otherWeeks.size)
        weeks.add(Pair(first = weekNo, second = otherWeeks[weekNo-1]))

    return weeks
}

fun getToday(): LocalDate {
    return try {
        LocalDate.now()
    } catch (e: KotlinException) { //Used cause of ZoneRulesException on Preview
        TEST_DATE.convert2FormattedLocalDate()
    }
}

fun getTodaysMyDate(): MyDate {
    return try {
        MyDate(date = LocalDate.now())
    } catch (e: KotlinException) { //Used cause of ZoneRulesException on Preview
        MyDate(date = TEST_DATE.convert2FormattedLocalDate())
    }
}

fun getTodaysDateTime(): LocalDateTime {
    return try {
        LocalDateTime.now()
    } catch (e: KotlinException) { //Used cause of ZoneRulesException on Preview
        TEST_DATE.convert2FormattedLocalDateTime()
    }
}

fun quarterlyMillis(localDate: LocalDate? = null): List<Long> {
    val date = localDate ?: getToday()
    val month: YearMonth = YearMonth.from(date)
    val start: LocalDateTime = month.minusMonths(1).atDay(1).atStartOfDay()
    val end: LocalDateTime = month.plusMonths(1).atEndOfMonth().atTime(23, 59, 59)
    return listOf(start.getMillis(), end.getMillis())
}

fun monthlyMillis(localDate: LocalDate? = null): List<Long> {
    val date = localDate ?: getToday()
    val month: YearMonth = YearMonth.from(date)
    val start: LocalDateTime = month.atDay(1).atStartOfDay()
    val end: LocalDateTime = month.atEndOfMonth().atTime(23, 59, 59)
    return listOf(start.getMillis(), end.getMillis())
}

fun dailyMillis(localDate: LocalDate? = null): List<Long> {
    val date = localDate ?: getToday()
    val start: LocalDateTime = date.atStartOfDay()
    val end: LocalDateTime = date.atTime(23, 59, 59)
    return listOf(start.getMillis(), end.getMillis())
}

fun millisInternalDates(
    startMillis: Long,
    endMillis: Long
): MutableList<LocalDate> {
    val dateList = mutableListOf<LocalDate>()
    val startDate = startMillis.convert2LocalDate()
    val endDate = endMillis.convert2LocalDate()
    val totalDays: Long = ChronoUnit.DAYS.between(startDate, endDate)
    for (day in 0..totalDays)
        dateList.add(startDate.plusDays(day))
    return dateList
}

fun LocalDate.isDatesEqual(givenDate: LocalDate = getToday()): Boolean = this == givenDate

fun LocalDate.isFromThisMonth(givenDate: LocalDate = getToday()): Boolean =
    month == givenDate.month && year == givenDate.year

fun String.convert2FormattedLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT))

fun String.convert2FormattedLocalDateTime(): LocalDateTime = //TODO: correct DATE_FORMAT
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT))

fun String.convert2DetailedLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(DETAILED_DATE_FORMAT))

fun LocalDate.convert2DetailedDate(): String =
    format(DateTimeFormatter.ofPattern(DETAILED_DATE_FORMAT))

fun LocalDate.convert2TimelineDate(): String =
    format(DateTimeFormatter.ofPattern(TIMELINE_DATE))

fun LocalDateTime.convert2DetailedDateTime(): String =
    format(DateTimeFormatter.ofPattern(DETAILED_DATE_TIME_FORMAT))

fun LocalDateTime.convert2DateTime(): String =
    format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))

fun LocalDateTime.getMillis(): Long =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.convert2LocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun Long.convert2LocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

fun Long.convert2DetailedDateTime(): String = convert2LocalDateTime().convert2DetailedDateTime()