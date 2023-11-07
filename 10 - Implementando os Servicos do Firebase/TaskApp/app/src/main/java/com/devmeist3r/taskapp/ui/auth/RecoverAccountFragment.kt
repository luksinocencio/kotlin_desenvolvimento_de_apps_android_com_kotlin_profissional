package com.devmeist3r.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentRecoverAccountBinding
import com.devmeist3r.taskapp.util.FirebaseHelper
import com.devmeist3r.taskapp.util.initToolbar
import com.devmeist3r.taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        auth = Firebase.auth
        initListeners()
    }

    private fun initListeners() {
        binding.btnSend.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressBar.isVisible = true
          recoverUser(email)
        } else {
            showBottomSheet(message =getString(R.string.email_empty))
        }
    }

    private fun recoverUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                binding.progressBar.isVisible = false
                if (task.isSuccessful) {
                    showBottomSheet(
                        message = getString(R.string.text_message_recover_account_fragment)
                    )
                } else {
                    showBottomSheet(
                        message = getString(FirebaseHelper.validError(task.exception?.message.toString()))
                    )
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}