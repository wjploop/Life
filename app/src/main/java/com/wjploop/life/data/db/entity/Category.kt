package com.wjploop.life.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "category_table",indices = [
    Index(name = "name",value = ["name"],unique = true)
])
data class Category(

    val name: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
