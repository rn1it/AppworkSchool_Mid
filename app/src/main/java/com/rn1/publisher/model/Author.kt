package com.rn1.publisher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    val email: String? = null,
    val id: String? = null,
    val name: String? = null
): Parcelable