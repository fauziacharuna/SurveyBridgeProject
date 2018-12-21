package com.example.fauziachmadharuna.surveybridgeproject.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.fauziachmadharuna.surveybridgeproject.R
import com.example.fauziachmadharuna.surveybridgeproject.core.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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

//        findViewById(R.id.email_sign_in_button).setOnClickListener { validateLogin() }

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
}


