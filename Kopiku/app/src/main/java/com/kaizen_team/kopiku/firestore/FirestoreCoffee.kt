package com.kaizen_team.kopiku.firestore

import com.kaizen_team.kopiku.model.Coffee

object FirestoreCoffee {
    const val collection = "Coffee"
    fun getCoffee(callback: (Boolean, List<Coffee>) -> Unit) {
        FirestoreIntance.instance.collection(collection).whereEqualTo("kategori", "Coffee").get()
            .addOnSuccessListener {
                var coffee= it.toObjects(Coffee::class.java)
                callback(true,coffee)
            }
            .addOnFailureListener {
                callback(false, listOf())
            }
    }

    fun getNonCoffee(callback: (Boolean, List<Coffee>) -> Unit) {
        FirestoreIntance.instance.collection(collection).whereEqualTo("kategori", "Non Coffee").get()
            .addOnSuccessListener {
                var coffee= it.toObjects(Coffee::class.java)
                callback(true,coffee)
            }
            .addOnFailureListener {
                callback(false, listOf())
            }
    }
}