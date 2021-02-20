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
    val category: String = "Default",
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,    // 作为主键插入时，若是0，则数据库为其生成一个Id，并在insert方法中作为返回值
    val content: String = "",
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
