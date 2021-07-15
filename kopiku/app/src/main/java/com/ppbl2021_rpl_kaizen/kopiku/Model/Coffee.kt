package com.ppbl2021_rpl_kaizen.kopiku.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coffee(
    var id: String? = null,
    var namaBarang: String = "",
    var hargabarang: Int = 0,
    var kategori: String = "",
    var fotoBarang: String = "",
    var deskripsi: String = ""
) :Parcelable
