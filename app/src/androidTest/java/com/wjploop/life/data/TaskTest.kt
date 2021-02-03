package com.wjploop.life.data

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wjploop.life.data.db.dao.CategoryDao
import com.wjploop.life.data.db.dao.TaskDao
import com.wjploop.life.data.db.LifeDatabase
import com.wjploop.life.data.db.entity.Category
import com.wjploop.life.data.db.entity.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class TaskTest {

    fun log(str: Any?) {
        Log.d("wolf", str.toString())
    }

    lateinit var db: LifeDatabase
    lateinit var taskDao: TaskDao
    lateinit var categoryDao: CategoryDao


    val categoryA = Category("Life")
    val categoryB = Category("Work")
    val categoryC = Category("Playing")


    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, LifeDatabase::class.java).build()
        taskDao = db.taskDao()
        categoryDao = db.categoryDao()

        categoryDao.insert(categoryA)
        categoryDao.insert(categoryB)
        categoryDao.insert(categoryC)
        log("insert 3 category")

        taskDao.insert(Task(categoryA.name, "Running in night"))
        taskDao.insert(Task(categoryB.name, "Complete Life app"))

        log("created")

    }

    @Test
    fun insertInValidTodo() = runBlocking {
        try {
            taskDao.insert(Task("Invalid category", "oh no"))
        } catch (e: Exception) {
            assert(e is SQLiteConstraintException)
        }
        queryTodo()
        log("ha")
    }

    @Test
    fun deleteCategory() = runBlocking {
        queryTodo()
        try {
            categoryDao.delete(categoryA)
        } catch (e: SQLiteConstraintException) {
            log("try to delete category but this category has todo")
            deleteAllTodoBelongToThisCategory(categoryA)
            categoryDao.delete(categoryA)
        }
        log("after delete category A")
        queryTodo()
    }

    suspend fun deleteAllTodoBelongToThisCategory(category: Category) {
        taskDao.listByCategory(categoryName = category.name).first().let {
            it.forEach {
                taskDao.delete(it)
            }
        }
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun queryCategory() = runBlocking {
        categoryDao.list().first().let {
            log(it)
        }
        log("oh")
    }

    @Test
    fun queryTodo() = runBlocking() {
        taskDao.list().first().let {
            log(it)
        }
        log("no")
    }
}