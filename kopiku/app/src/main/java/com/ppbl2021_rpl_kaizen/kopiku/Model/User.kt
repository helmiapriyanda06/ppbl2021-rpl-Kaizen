package com.ppbl2021_rpl_kaizen.kopiku.Model

import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    val id : String? =  "",
    val username : String = "",
    val email : String = "",
    val password: String = ""
)