package com.kaizen_team.kopiku.model

import com.google.firebase.firestore.DocumentId

class User (
    @DocumentId
    val id : String? =  "",
    val email : String = "",
    val password: String = ""
)