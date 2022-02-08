package com.example.instaflix.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instaflix.ProgressBarDialogFragment
import com.example.instaflix.databinding.FragmentSettingsBinding
import com.example.instaflix.R
import com.parse.ParseException
import com.parse.ParseUser

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //buttons for the fragment
        val logoutButton: Button = requireActivity().findViewById<Button>(R.id.btn_logout)
        val usernameTextView: TextView =
            requireActivity().findViewById<TextView>(R.id.txt_username)

        var bundle = requireActivity().getIntent().getExtras()
        val user: ParseUser = bundle?.get("user") as ParseUser
        usernameTextView.text = user.username

        logoutButton.setOnClickListener {
            onClickLogout()
        }
    }

    private fun onClickLogout() {
        val newFragment = ProgressBarDialogFragment()
        newFragment?.show(childFragmentManager, "progressBar")
        ParseUser.logOutInBackground {e: ParseException? ->
            newFragment?.dismiss()
            if (e == null) {
                Toast.makeText(activity, "Sign out successful", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }
}