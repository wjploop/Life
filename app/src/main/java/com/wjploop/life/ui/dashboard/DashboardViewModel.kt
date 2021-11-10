package com.wjploop.life.ui.dashboard

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjploop.life.data.db.entity.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(val app: Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun startLoad() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                app.packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES)
                    .asFlow()
                    .collect {
                        if (it.applicationInfo != null
                            && it.applicationInfo.name != null
                            && it.applicationInfo.icon != 0
                            && app.packageManager.getLaunchIntentForPackage(it.packageName) != null
                        ) {
                            AppItem(
                                it.packageName,
                                it.applicationInfo.loadLabel(app.packageManager),
                                it.applicationInfo.loadIcon(app.packageManager),
                            ).let {
//                                delay(50)
//                            Log.d("wolf","emit $it")
                                val list = mutableListOf<AppItem>()
                                apps.value?.let {
                                    list.addAll(it.toList())
                                }
                                list.add(it)
                                Log.d("wolf", "list size ${list.size}")
                                apps.postValue(list)

                            }
                        }
                    }
            }
        }
    }


    val apps: MutableLiveData<MutableList<AppItem>> by lazy {
        MutableLiveData<MutableList<AppItem>>()
    }
}