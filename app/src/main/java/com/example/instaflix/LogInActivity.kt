package com.example.instaflix


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUp = findViewById<Button>(R.id.signUpButton)
        val btnLogIn = findViewById<Button>(R.id.logInButton)
        val editTextUsername = findViewById<EditText>(R.id.useranmeInputEditText)
        val editTextPassword = findViewById<EditText>(R.id.passwordInputEditText)

        btnSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "Sign Up Button Clicked", Toast.LENGTH_LONG)
                .show()

        }

        btnLogIn.setOnClickListener {
            Toast.makeText(applicationContext, "Log In Button Clicked", Toast.LENGTH_LONG)
                .show()
        }



    }

}