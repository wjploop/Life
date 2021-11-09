package com.wjploop.life.ui.dashboard

import android.app.Application
import android.content.pm.PackageInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel(val app: Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val packages: MutableLiveData<List<PackageInfo>> by lazy {
        MutableLiveData<List<PackageInfo>>().apply {
            app.packageManager.getInstalledPackages(0).let {
                postValue(it)
            }
        }
    }
}