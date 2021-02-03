package com.wjploop.life.data.db.dao

import androidx.room.*
import com.wjploop.life.data.db.entity.Category
import com.wjploop.life.data.db.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: Category): Long

    @Delete
    fun delete(category: Category)

    fun delete(categoryName: String) {
        delete(Category(categoryName))
    }

    @Update
    fun update(category: Category)

    @Query("select * from category_table")
    fun list(): Flow<List<Category>>


}