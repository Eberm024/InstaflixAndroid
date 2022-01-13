package com.example.instaflix

import android.app.Dialog
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class ProgressBarDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            //layout inflater
            val inflater = requireActivity().layoutInflater;
            //Inflate
            builder.setView(inflater.inflate(R.layout.dialog_progressbar, null))
            //any items and action setups below
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}