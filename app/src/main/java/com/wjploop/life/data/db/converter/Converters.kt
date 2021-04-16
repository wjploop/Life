package com.wjploop.life.data.db.converter

import android.util.ArraySet
import androidx.room.TypeConverter
import com.wjploop.life.data.db.entity.ImageEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class Converters {


    @TypeConverter
    fun calendar2timestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun timestamp2Calendar(timeValue: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = timeValue }

    @TypeConverter
    fun imageEntity2Str(imageEntity: List<ImageEntity>):String = Json.encodeToString(imageEntity)

    @TypeConverter
    fun str2ImageEntity(imageStr:String):List<ImageEntity> = Json.decodeFromString(imageStr)


}