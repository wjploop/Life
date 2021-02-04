package com.wjploop.life.data.repository

import com.wjploop.life.App
import com.wjploop.life.data.db.dao.TaskDao

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

    fun list() = taskDao.list();

    fun listByCategory(categoryName: String) = taskDao.listByCategory(categoryName)


}