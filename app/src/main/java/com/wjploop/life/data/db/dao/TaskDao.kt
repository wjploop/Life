package com.wjploop.life.data.db.dao

import androidx.room.*
import com.wjploop.life.data.db.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("select * from `task_table`")
    fun list(): Flow<List<Task>>

    @Query("select * from `task_table` where category = :categoryName")
    fun listByCategory(categoryName: String): Flow<List<Task>>

    @Query("select * from task_table where id=:id")
    fun taskById(id: Int): Flow<Task>

    @Delete
    fun delete(task: Task)

    @Query("delete from task_table where id = :taskId")
    fun deleteById(taskId: Long)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: Task): Long

}