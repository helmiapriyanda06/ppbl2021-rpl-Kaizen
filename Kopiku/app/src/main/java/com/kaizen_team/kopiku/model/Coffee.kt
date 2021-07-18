package com.kaizen_team.kopiku.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coffee(
    @DocumentId
    var id: String? = null,
    var nama_barang: String = "",
    var harga_barang: Int? = null,
    var kategori: String = "",
    var fotoBarang: String = "",
    var deskripsi: String = ""
): Parcelable

