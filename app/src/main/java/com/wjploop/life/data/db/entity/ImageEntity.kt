package com.wjploop.life.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(val id: String, val path: String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(path)
    }

    companion object CREATOR : Parcelable.Creator<ImageEntity> {
        override fun createFromParcel(parcel: Parcel): ImageEntity {
            return ImageEntity(parcel)
        }

        override fun newArray(size: Int): Array<ImageEntity?> {
            return arrayOfNulls(size)
        }
    }
}
