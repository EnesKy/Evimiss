package com.eky.evimiss.utils

import androidx.core.content.edit
import com.eky.evimiss.App
import com.eky.evimiss.data.model.CalendarEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Singleton

@Singleton
object SharedPrefUtil {

    private const val IS_FIRST_TIME = "IS_FIRST_TIME"
    private const val MAIN_CALENDAR_ENTITY = "MAIN_CALENDAR_ENTITY"
    private const val MAIN_CALENDAR_LIST = "MAIN_CALENDAR_LIST"

    var isFirstTime: Boolean
        get() = App.mPrefs?.getBoolean(IS_FIRST_TIME, true).isTrue()
        set(value) = App.mPrefs!!.edit { putBoolean(IS_FIRST_TIME, value) }

    var mainCalendarEntity: CalendarEntity
        get() = Gson().fromJson(App.mPrefs?.getString(MAIN_CALENDAR_ENTITY, ""), object : TypeToken<CalendarEntity>() {}.type)
        set(value) = App.mPrefs!!.edit { putString(MAIN_CALENDAR_ENTITY, Gson().toJson(value)) }

    var mainCalendarList: List<CalendarEntity>
        get() = Gson().fromJson(App.mPrefs?.getString(MAIN_CALENDAR_LIST, ""), object : TypeToken<List<CalendarEntity>>() {}.type)
        set(value) = App.mPrefs!!.edit { putString(MAIN_CALENDAR_LIST, Gson().toJson(value)) }

    fun clearAll() {
        App.mPrefs?.edit()?.apply{
            clear()
            apply()
        }
    }

}

