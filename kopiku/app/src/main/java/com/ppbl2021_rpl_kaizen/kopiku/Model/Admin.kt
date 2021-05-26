package com.ppbl2021_rpl_kaizen.kopiku.Model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Admin(
    var id: String? = null,
    var name: String? = null,
    var password: String? = null,
    var role: String? = null,
) :Parcelable
