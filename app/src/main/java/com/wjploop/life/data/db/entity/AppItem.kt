package com.wjploop.life.data.db.entity

import android.graphics.drawable.Drawable

data class AppItem(
    val packageName: String,
    val name: CharSequence,
    val icon: Drawable,
)