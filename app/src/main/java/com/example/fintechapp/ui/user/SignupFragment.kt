package com.example.fintechapp.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fintechapp.R
import com.example.fintechapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Register Button Click Listener
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etPassword2.text.toString().trim()

            if (validateInputs(email, password, confirmPassword)) {
                registerUser(email, password)
            }
        }
    }

    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        // Validate Email
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }

        // Validate Password
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }

        // Validate Confirm Password
        if (password != confirmPassword) {
            binding.etPassword2.error = "Passwords do not match"
            return false
        }

        return true
    }

    private fun registerUser(email: String, password: String) {
        // Attempt Firebase Registration
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Success: Navigate to LoginFragment
                Toast.makeText(requireContext(), "Signup Successful", Toast.LENGTH_SHORT).show()
                Log.d("SignupFragment", "User registered successfully")
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            } else {
                // Failure: Show Error Message
                val errorMessage = task.exception?.message ?: "Signup failed"
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                Log.e("SignupFragment", "Registration failed: $errorMessage")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
