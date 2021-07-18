package com.kaizen_team.kopiku.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kaizen_team.kopiku.firestore.FirestoreUser

class LoginViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    val pesan = MutableLiveData<String>()
    val sukses = MutableLiveData<Boolean>()

    fun messageListener(): LiveData<String> = pesan
    fun successListener(): LiveData<Boolean> = sukses

    fun login(email: String, password: String) {
        FirestoreUser.getUserByUsernameAndPassword(email, password) {
            if (it != null) {
                auth.signInWithEmailAndPassword(it.email, password).addOnCompleteListener { it2 ->
                    if (it2.isSuccessful) {
                        sukses.value = true
                    } else {
                        pesan.value = "Username atau Password yang anda masukan salah"
                    }
                }
            } else {
                pesan.value = "Username atau Password yang anda masukan salah"
            }
        }
    }
}