package com.wjploop.life.data.db.entity

import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.Calendar

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(entity = Category::class, parentColumns = ["name"], childColumns = ["category"])
    ],
    indices = [Index("category")]
)
data class Task(
    val category: String,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,    // 作为主键插入时，若是0，则数据库为其生成一个Id，并在insert方法中作为返回值
    val created: Calendar = Calendar.getInstance(),
    @IntRange(from = 1, to = 100)
    val scored: Int = 1, //  奖励几朵小红花
) {
    override fun toString(): String {
        return "Task(category=$category, title=$title, created=${
            DateFormat.getInstance().format(created.time)
        } )"
    }
}