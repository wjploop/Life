package com.wjploop.life.ui.edit

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.data.repository.TaskRepo
import kotlin.properties.Delegates


class EditTaskViewModel(val taskId: Long) : ViewModel() {

    val repo: TaskRepo by lazy {
        TaskRepo()
    }

    val task = repo.taskById(taskId)

    val editingTask by lazy {
        task.value?.copy()
    }


    val change = MutableLiveData(false)

    companion object {
        class Factory(val taskId: Long) : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditTaskViewModel(taskId) as T
            }
        }
    }

}