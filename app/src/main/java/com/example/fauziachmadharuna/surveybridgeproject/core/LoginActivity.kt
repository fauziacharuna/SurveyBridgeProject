package com.example.fauziachmadharuna.surveybridgeproject.core

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.view.HomeActivity
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var login: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mAuth = FirebaseAuth.getInstance()
        login = object : LoginManager(mAuth) {
            override fun isSuccessLogin() {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }

            override fun isFailedLogin() {
                Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
            }

        }

        btn_signin.setOnClickListener { validateLogin() }

    }

    override fun onStart() {
        super.onStart()
        login.onStart()
    }

    override fun onStop() {
        super.onStop()
        login.onStop()
    }

    private fun validateLogin() {
        val inputEmail = findViewById(R.id.email) as EditText
        val inputPassword = findViewById(R.id.password) as EditText
        val REQUIRED = "Required"

        if (inputEmail.text.toString().isEmpty()) {
            inputEmail.error = REQUIRED
        } else if (inputPassword.text.toString().isEmpty()) {
            inputPassword.error = REQUIRED
        } else {
            login.go(inputEmail.text.toString(), inputPassword.text.toString())
        }
    }
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
}


