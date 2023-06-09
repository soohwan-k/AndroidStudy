package org.tech.town.bookreview.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//Parcelize -> intent 로 book 넘길 때 object 를 직렬화
@Parcelize
data class Book(
    @SerializedName("isbn") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val priceSales: String?,
    @SerializedName("image") val coverSmallUrl: String?,
    @SerializedName("link") val mobileLink: String?
) : Parcelable