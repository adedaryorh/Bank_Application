package com.example.fintechapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fintechapp.MainActivity
import com.example.fintechapp.R
import com.example.fintechapp.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


/**
 * A simple [Fragment] subclass.
 * Use the [LoginActivity] factory method to
 * create an instance of this fragment.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val logTag = "LoginActivity"
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
        ) { res ->
        this.onSignInResult(res)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setupLogin()
    }


    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            if (auth.currentUser != null) {
                navigateHome()
            } else {
                createSignInIntent()
            }
        }
    }

    private fun navigateHome() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }


    private fun createSignInIntent() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
        )
        // Create and launch sign-in intent
        val signInIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build()
        signInLauncher.launch(signInIntent)
    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {

            // User signed in successfully
            val user = auth.currentUser
            user?.let {
                //storeUserData(it)
            }
            navigateHome()

        } else {
            val errorCode = response?.error?.errorCode
            if (response == null) {
                Toast.makeText(
                    this,
                    getString(R.string.sign_in_failed), Toast.LENGTH_LONG
                ).show()

                //Timber.tag(logTag).d(errorCode.toString())
                Log.d(logTag, "Failed - Error Code: $errorCode")
            }
            when (errorCode) {
                ErrorCodes.NO_NETWORK -> {
                    //Show No Internet Notification
                    Toast.makeText(this, "No internet: $errorCode", Toast.LENGTH_LONG)
                        .show()
                    Log.d(logTag, "Failed - Error Code: $errorCode")
                    return
                }
                else -> {
                    Toast.makeText(this, errorCode.toString(), Toast.LENGTH_LONG).show()
                    //Timber.tag(logTag).d("Failed - Error Code: $errorCode")
                    Log.d(logTag, "Failed - Error Code: $errorCode")

                    return
                }
            }
        }
    }
}
