package com.ppbl2021_rpl_kaizen.kopiku.Model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coffee(
    var id: String? = null,
    var namaBarang: String = "",
    var hargaBarang: String = "",
    var kategori: String = "",
    var fotoBarang: String = "",
    var deskripsi: String = ""
) :Parcelable
