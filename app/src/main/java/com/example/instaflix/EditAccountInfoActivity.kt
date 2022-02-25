package com.example.instaflix

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.parse.Parse
import com.parse.ParseException
import com.parse.ParseUser

class EditAccountInfoActivity : AppCompatActivity() {

    private var usernameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var previousPasswordEditText: EditText? = null
    private var newPasswordEditText: EditText? = null
    private var newPasswordConfirmEditText: EditText? = null
    private var editProfileImageButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account_info)

        usernameEditText = findViewById<EditText>(R.id.editText_edit_account_username)
        emailEditText = findViewById<EditText>(R.id.editText_edit_account_email)
        previousPasswordEditText =
            findViewById<EditText>(R.id.editText_edit_account_previous_password)
        newPasswordEditText = findViewById<EditText>(R.id.editText_edit_account_new_password)
        newPasswordConfirmEditText =
            findViewById<EditText>(R.id.editText_edit_account_new_password_confirm)
        editProfileImageButton = findViewById<ImageButton>(R.id.imageButton_edit_account_profile)

    }

    private fun btnCancelOnClick(view: View) {
        //return to last screen, kill current activity
    }

    private fun btnConfirmOnClick(view: View) {
        //check inputs of the text fields and apply accordingly
        var failedLogin = false
        val user = ParseUser.getCurrentUser()

        //attempt a login and if that doesn't work then password is wrong
        ParseUser.logInInBackground(user.username, previousPasswordEditText?.text.toString())
        { parseUser: ParseUser?,
          error: ParseException? ->
            if (parseUser != null) {
                Log.d("ParseLogin", "correct previous password")
            }
            else {
                Toast.makeText(this, "Incorrect previous password", Toast.LENGTH_SHORT)
                failedLogin = true
            }
        }

        //check any other entries and properties
        if(failedLogin) {
            return
        }

        if(usernameEditText?.text.toString() != "") {
            //Update call for Parse Back4app
        }
        if(emailEditText?.text.toString() != "") {
            //Update call for Parse Back4app
        }
        if(newPasswordEditText?.text.toString() == newPasswordConfirmEditText?.text.toString()) {
            //Update call for Parse Back4app

        }
        //compare if editImage id is the same as the default account image
        //create a drawable for a default ic_menu_camera
        val icCameraDrawable: Drawable? = ResourcesCompat.getDrawable(application.resources,
            R.drawable.ic_menu_camera,
            null)

        val bitmapICCamera = icCameraDrawable?.toBitmap()
        val bitmapCurrentImage = editProfileImageButton?.foreground?.toBitmap()

        //bitmap comparison but still have to avoid nulls
        if (bitmapCurrentImage != null) {
            if(bitmapCurrentImage.sameAs(bitmapICCamera)) {
                Log.d("bitmapComparison", "It is the default image, do not update image")
            }
            else {
                //Update call for Parse Back4app
            }
        }
    }

}