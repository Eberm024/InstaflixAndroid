/* next thing to work on is FIX LOGIN and SIGNUP,sharedpreferences, test homeActivity, test fragments after log in,
* test settings fragment*/
package com.example.instaflix


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback

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
            val username = editTextUsername.text.toString()
            val pass = editTextPassword.text.toString()
            val user = ParseUser()
            user.username = username
            user.setPassword(pass)

            //alertDialog
            val newFragment = ProgressBarDialogFragment()
            newFragment?.show(supportFragmentManager, "progressBar")

            user.signUpInBackground(SignUpCallback { error ->
                newFragment?.dismiss()

                if (error == null) {
                    Toast.makeText(applicationContext, "Sign Up completed", Toast.LENGTH_LONG)
                        .show()

                    val intent = Intent(this@LogInActivity, HomeActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
                else {
                    ParseUser.logOut()
                    Toast.makeText(applicationContext, "Sign Up failed", Toast.LENGTH_LONG)
                        .show()
                    println("Sign Up failed")
                    Log.e("Parse_result", error.toString())
                }
            })

        }

        btnLogIn.setOnClickListener {
            Toast.makeText(applicationContext, "Log In Button Clicked", Toast.LENGTH_LONG)
                .show()

            val username = editTextUsername.getText().toString()
            val pass = editTextPassword.getText().toString()
            val user = ParseUser()
            user.username = username
            user.setPassword(pass)

            //alertDialog
            val newFragment = ProgressBarDialogFragment()
            newFragment?.show(supportFragmentManager, "progressBar")


            /* this works, I was missing some alertDialog (progressBarDialog got deprecated)
            to stop the app so that it could finish
            loading in */

            ParseUser.logInInBackground(username, pass) { parseUser: ParseUser?,
                                                          parseException: ParseException? ->
                newFragment?.dismiss()
                if (parseUser != null) {
                    val intent = Intent(this@LogInActivity, HomeActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
                else {
                    ParseUser.logOut()
                    if(parseException != null) {
                        println("error in login")
                        // println(user.toString()) //user may be null object this line also crashes
                        Log.e("Parse_result", parseException.toString()) // com.parse.ParseRequest$ParseRequestException: i/o failure
                        Toast.makeText(applicationContext, "Log In failed", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

}