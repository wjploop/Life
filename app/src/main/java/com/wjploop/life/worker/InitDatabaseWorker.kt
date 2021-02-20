package com.wjploop.life.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wjploop.life.App
import com.wjploop.life.data.db.entity.Category
import com.wjploop.life.data.db.entity.Task
import kotlinx.coroutines.coroutineScope

class InitDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val initCategory = listOf(
        "Default", "Life", "Work", "Play"
    ).map { Category(it) }
    private val initTask = listOf(
        Task(category = initCategory[0].name, title = "早睡，12点钱入睡"),
        Task(initCategory[0].name, "早起，12点钱入睡"),
        Task(initCategory[1].name, "Complete Life App"),
        Task(initCategory[2].name, "Play Lol 2 hours"),
    )

    override suspend fun doWork(): Result {
        return coroutineScope {
            println("do worker")
            App.app.getDataBase().run {
                initCategory.forEach {
                    categoryDao().insert(it)
                }
                initTask.forEach {
                    taskDao().insert(it)
                }
            }
            Result.success()
        }
    }
}