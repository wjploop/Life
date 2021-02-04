package com.wjploop.life.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wjploop.life.data.db.entity.Task
import com.wjploop.life.data.repository.TaskRepo

class HomeViewModel : ViewModel() {

    val repo: TaskRepo by lazy {
        TaskRepo()
    }

    val tasks = repo.list().asLiveData()


}