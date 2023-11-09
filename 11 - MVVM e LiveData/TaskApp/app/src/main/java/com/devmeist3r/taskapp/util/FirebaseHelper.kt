package com.devmeist3r.taskapp.util

import com.devmeist3r.taskapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class FirebaseHelper {
    companion object {
        fun getDatabase() = Firebase.database.reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = getAuth().currentUser?.uid ?: ""
        fun isAutenticated() = getAuth().currentUser != null
        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }
                error.contains("The password is invalid or the user does not have a password") -> {
                    R.string.invalid_password_register_fragment
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.strong_password_register_fragment
                }
                else -> {
                    R.string.error_generic
                }
            }
        }
    }
}