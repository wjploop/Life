package com.wjploop.life.ui.category

import androidx.lifecycle.ViewModel
import com.wjploop.life.data.repository.TaskRepo

class CategoryViewModel : ViewModel() {

    val repo: TaskRepo by lazy {
        TaskRepo()
    }

    val categoryList = repo.categories()
}