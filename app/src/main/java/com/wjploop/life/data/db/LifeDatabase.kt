package com.wjploop.life.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wjploop.life.data.db.converter.Converters
import com.wjploop.life.data.db.dao.CategoryDao
import com.wjploop.life.data.db.dao.TaskDao
import com.wjploop.life.data.db.entity.Category
import com.wjploop.life.data.db.entity.Task

@Database(entities = [Task::class, Category::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LifeDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        @Volatile
        private var instance: LifeDatabase? = null

        fun getInstance(context: Context): LifeDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): LifeDatabase {
            return Room.databaseBuilder(context, LifeDatabase::class.java, "life_db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                    }
                }).build()

        }
    }
}