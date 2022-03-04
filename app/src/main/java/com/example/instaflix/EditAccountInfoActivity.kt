package com.example.instaflix

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.parse.*
import java.io.ByteArrayOutputStream

class EditAccountInfoActivity : AppCompatActivity() {

    private var usernameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var previousPasswordEditText: EditText? = null
    private var newPasswordEditText: EditText? = null
    private var newPasswordConfirmEditText: EditText? = null
    private var editProfileImageButton: ImageButton? = null

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE )

    private var selectedImageBytes: ByteArray? = null

    private val startForResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data

            //Handle Intent
            val selectedImageUri = intent?.data
            val source = selectedImageUri?.let { ImageDecoder.createSource(contentResolver, it) }
            editProfileImageButton?.foreground = source?.let { ImageDecoder.decodeDrawable(it) }


            //convert bitmap directly to bytesArray, and save the data for user later
            val bitmapCurrentImage = editProfileImageButton?.foreground?.toBitmap()
            val baos = ByteArrayOutputStream()
            bitmapCurrentImage?.compress(Bitmap.CompressFormat.PNG, 100, baos)
            selectedImageBytes = baos.toByteArray()
        }
    }

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
            btnCancelOnClick()
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
        finish()
    }

    private fun btnConfirmOnClick() {
        //check inputs of the text fields and apply accordingly
        val user = ParseUser.getCurrentUser()

        //alertDialog
        val progressBarFragment = ProgressBarDialogFragment()
        progressBarFragment.show(supportFragmentManager, "progressBar")

        if(usernameEditText?.text.toString() != "") {
            //Update username
            user.username = usernameEditText?.text.toString()
        }
        if(emailEditText?.text.toString() != "") {
            //Update email
            user.email = emailEditText?.text.toString()
        }
        if(newPasswordEditText?.text.toString() == newPasswordConfirmEditText?.text.toString()
            && newPasswordEditText?.text.toString() != ""
            && newPasswordConfirmEditText?.text.toString() != "" ) {
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
                Log.d("BitmapComparison", "It is a new image, update image in Back4App servers")
                val file = ParseFile( "${user.username}_profile.png", selectedImageBytes)
                file.saveInBackground()
                user.put("profilePicture",file)
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
        uploadTask()
    }

    private fun uploadTask() {
        //trigger the image upload stuff
        verifyStoragePermissions(this)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForResults.launch(Intent.createChooser(intent, "Open Gallery"))
    }

    private fun verifyStoragePermissions(activity: Activity) {
        val permission = ActivityCompat.checkSelfPermission(activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permission != PackageManager.PERMISSION_GRANTED) {
            //if we are in here, then we don't have permissions, so we prompt the user
            ActivityCompat.requestPermissions(activity,
                PERMISSION_STORAGE,
                REQUEST_EXTERNAL_STORAGE)
        }
    }
}