package com.wjploop.life.data.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(val id: String, val path: String): java.io.Serializable
