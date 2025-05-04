package com.iglesiabethesta.bethestaapp.data.network

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton

/*aqui para no estar llamando
* en cada clase la instancia de auth y la db firestore*/
@Singleton
class FirebaseClient @Inject constructor() {

    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    val db = Firebase.firestore
}