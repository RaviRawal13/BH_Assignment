package com.ravirawal.bheroassignment.model_ui

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil

object BeerDiffCallBack : DiffUtil.ItemCallback<BeerUi>() {
    override fun areItemsTheSame(
        oldItem: BeerUi,
        newItem: BeerUi
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: BeerUi,
        newItem: BeerUi
    ) = oldItem == newItem
}

data class BeerUi(
    val id: Int?,
    val imageUrl: String?,
    val name: String?,
    var isChecked: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt() as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean ?: false
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: -1)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeValue(isChecked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeerUi> {
        override fun createFromParcel(parcel: Parcel): BeerUi {
            return BeerUi(parcel)
        }

        override fun newArray(size: Int): Array<BeerUi?> {
            return arrayOfNulls(size)
        }
    }
}