package com.example.instaflix

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        val btnCancel = findViewById<Button>(R.id.btn_edit_account_cancel)
        val btnConfirm = findViewById<Button>(R.id.btn_edit_account_confirm)

        btnCancel.setOnClickListener {
            btnCancelOnClick();
        }

        btnConfirm.setOnClickListener {
            btnConfirmOnClick()
        }

        editProfileImageButton?.setOnClickListener {
            btnImageOnClick()
        }

    }

    private fun btnCancelOnClick() {
        //return to last screen, kill current activity
    }

    private fun btnConfirmOnClick() {
        //check inputs of the text fields and apply accordingly
        var failedLogin = false
        val user = ParseUser.getCurrentUser()

        //alertDialog
        val progressBarFragment = ProgressBarDialogFragment()
        progressBarFragment?.show(supportFragmentManager, "progressBar")

        if(usernameEditText?.text.toString() != "") {
            //Update username
            user.username = usernameEditText?.text.toString()
        }
        if(emailEditText?.text.toString() != "") {
            //Update email
            user.email = emailEditText?.text.toString()
        }
        if(newPasswordEditText?.text.toString() == newPasswordConfirmEditText?.text.toString()) {
            //Update password
            user.setPassword(newPasswordConfirmEditText?.text.toString())

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
                //Update File for Parse Back4app
            }
        }

        //Save Object
        user.saveInBackground { error ->
            //dismiss
            progressBarFragment.dismiss()
            if(error == null) {
                Toast.makeText(this, "Success on saving user info!",
                    Toast.LENGTH_SHORT).show()
                Log.d("ParseObjectSave", "saving user object successful!")
            }
            else {
                Toast.makeText(this, "Error on saving account info, try again later",
                    Toast.LENGTH_SHORT).show()
                Log.e("ParseObjectSave", "Error on saving user object: ${error.message}")
            }
        }
    }

    private fun btnImageOnClick() {
        //trigger the image upload stuff
    }
}