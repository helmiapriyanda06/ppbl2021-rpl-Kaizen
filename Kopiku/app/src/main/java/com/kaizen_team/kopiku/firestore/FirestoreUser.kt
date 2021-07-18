package com.kaizen_team.kopiku.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.kaizen_team.kopiku.model.User

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