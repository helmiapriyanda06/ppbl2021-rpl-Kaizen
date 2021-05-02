package com.ppbl2021_rpl_kaizen.kopiku.Firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.ppbl2021_rpl_kaizen.kopiku.Model.User

object FirestoreUser {
    const val COLLECTON = "user"

    fun getUserByUsernameAndPassword(
        email: String,
        password: String,
        onResult: (User?) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(COLLECTON).whereEqualTo("email", email)
            .whereEqualTo("password", password).get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    onResult(null)
                } else {
                    onResult(it.documents[0].toObject(User::class.java))
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}