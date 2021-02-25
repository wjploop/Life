package com.wjploop.life.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wjploop.life.data.repository.TaskRepo

class HomeViewModel : ViewModel() {

    val repo: TaskRepo by lazy {
        TaskRepo()
    }

    val tasks = repo.tasks()

    var showInput = MutableLiveData(false)

    fun toggleInput() {
        showInput.value = !showInput.value!!
    }



}