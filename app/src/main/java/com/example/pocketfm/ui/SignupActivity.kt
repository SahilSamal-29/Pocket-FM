package com.example.pocketfm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.pocketfm.databinding.ActivitySignupBinding
import com.example.pocketfm.ui.HomeActivity
import com.example.pocketfm.ui.OtpVerifyActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import java.util.concurrent.TimeUnit
import kotlin.jvm.java

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupClickListeners()
    }

    // 🔥 Click handlers
    private fun setupClickListeners() {

        // Continue with OTP
        binding.btnCreateAccount.setOnClickListener {
            handleSignup()
        }

        // Google Sign-In
        binding.btnGoogleSignUp.setOnClickListener {
            launchGoogleSignIn()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // 🔐 Signup validation + OTP
    private fun handleSignup() {

        val name = binding.etFullName.text.toString().trim()
        val phone = binding.etMobileNumber.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val gender = binding.rgGender

        if (name.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.length != 10) {
            Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("SIGNUP", "Name: $name Phone: $phone")

        sendOtp(phone)
    }

    // 📱 Send OTP
    private fun sendOtp(phone: String) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("OTP_ERROR", e.message.toString())
                    Toast.makeText(this@SignupActivity, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(
                    verId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = verId

                    // 🔥 Move to OTP screen
                    val intent = Intent(this@SignupActivity, OtpVerifyActivity::class.java)
                    intent.putExtra("verificationId", verId)
                    intent.putExtra("phone", phone)
                    startActivity(intent)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // 🔓 Sign in after OTP auto-verification
    private fun signInWithCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 🔐 Google Sign-In

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        onSignInResult(result)
    }

    private fun launchGoogleSignIn() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(intent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            updateUI(user)
        } else {
            Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    // 🚀 Navigate after success
    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            Log.d("AUTH", "Login Success")

            startActivity(Intent(this, HomeActivity::class.java))
            finish()

        } else {
            Log.e("AUTH", "User is null")
        }
    }
}