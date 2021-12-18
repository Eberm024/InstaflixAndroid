package com.example.instaflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.parse.Parse

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_url))
                .server(getString(R.string.back4app_server_url))
                .build()
        )

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, LogInActivity::class.java)
            intent.putExtra("keyIntent1", "Welcome to the Main Screen")
            startActivity(intent)
            finish()
        }, 3000)

    }
}