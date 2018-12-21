package com.example.fauziachmadharuna.surveybridgeproject.view

import com.google.firebase.auth.FirebaseAuth

abstract class LoginManager(val auth: FirebaseAuth) {
    val mAuthListener = FirebaseAuth.AuthStateListener({ firebaseAuth ->
        val user = firebaseAuth.currentUser
        if (user != null) isSuccessLogin()
    })

    abstract fun isSuccessLogin()

    fun go(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { complete ->
                if (!complete.isSuccessful) isFailedLogin()
            }
    }

    abstract fun isFailedLogin()

    fun onStart() {
        auth.addAuthStateListener(mAuthListener)
    }

    fun onStop() {
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener)
        }
    }
}