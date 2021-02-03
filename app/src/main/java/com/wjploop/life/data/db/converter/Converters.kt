package com.wjploop.life.data.db.converter

import androidx.room.TypeConverter
import java.util.*

class Converters {


    @TypeConverter
    fun calendar2timestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun timestamp2Calendar(timeValue: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = timeValue }
}