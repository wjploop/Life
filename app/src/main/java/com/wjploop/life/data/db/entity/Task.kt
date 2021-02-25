package com.wjploop.life.data.db.entity

import androidx.annotation.IntRange
import androidx.room.*
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
    @ColumnInfo(defaultValue = "Default")
    var category: String = "Default",
    var title: String,
    var isComplete: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,    // 作为主键插入时，若是0，则数据库为其生成一个Id，并在insert方法中作为返回值
    var content: String = "",
    val created: Calendar = Calendar.getInstance(),
    @IntRange(from = 1, to = 100)
    val scored: Int = 1, //  奖励几朵小红花
) : Comparable<Task> {
    override fun toString(): String {
        return "Task(category=$category, title=$title, created=${
            DateFormat.getInstance().format(created.time)
        } )"
    }

    override fun compareTo(other: Task): Int {
        return if (isComplete && !other.isComplete) {
            1
        } else if (!isComplete && other.isComplete) {
            -1
        } else {
            title.compareTo(other.title)
        }
    }
}

fun Calendar.toString():String {
    return DateFormat.getInstance().format(time)
}
