package com.wjploop.life.data.repository

import com.wjploop.life.App
import com.wjploop.life.data.db.dao.CategoryDao
import com.wjploop.life.data.db.dao.TaskDao
import com.wjploop.life.data.db.entity.Task

/**
 *  为什么需要加这么一层Repo呢？
 *  为什么不直接操作数据库？
 *
 *  数据源可能加入网络或其他
 *
 */

class TaskRepo constructor() {

    private val taskDao: TaskDao by lazy() {
        App.app.getDataBase().taskDao()
    }
    private val categoryDao: CategoryDao by lazy {
        App.app.getDataBase().categoryDao()
    }


    fun tasks() = taskDao.list()

    fun taskById(id: Long) = taskDao.taskById(id)

    fun listByCategory(categoryName: String) = taskDao.listByCategory(categoryName)


    suspend fun addTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun removeTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    suspend fun completeTask(task: Task) {
        taskDao.update(task.copy(isComplete = true))
    }


    fun categories() = categoryDao.list()
}