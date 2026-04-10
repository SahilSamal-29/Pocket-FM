package com.example.pocketfm.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.GetCredentialRequest
import com.example.pocketfm.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import kotlin.jvm.java

class LoginActivity : AppCompatActivity() {
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var auth: FirebaseAuth
//    private var verificationId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

//    private val launcher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//            if (result.resultCode == RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//
//                try {
//                    val account = task.getResult(ApiException::class.java)
//                    firebaseAuthWithGoogle(account.idToken!!)
//                } catch (e: Exception) {
//                    Log.e("GOOGLE_SIGNIN", "Failed", e)
//                }
//            }
//        }
//
//    fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        launcher.launch(signInIntent)
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d("AUTH", "Google Sign-In Success")
//                } else {
//                    Log.e("AUTH", "Failed")
//                }
//            }
//    }
//
//    private fun setupGoogle() {
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//    }
//
//    fun sendOtp(phone: String) {
//
//        auth = FirebaseAuth.getInstance()
//
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber("+91$phone")
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    signInWithCredential(credential)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    Log.e("OTP", "Failed: ${e.message}")
//                }
//
//                override fun onCodeSent(
//                    verId: String,
//                    token: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    verificationId = verId
//                    Log.d("OTP", "Code Sent")
//                }
//            })
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun verifyOtp(code: String) {
//        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
//        signInWithCredential(credential)
//    }
//
//    private fun signInWithCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d("OTP", "Login Success")
//                } else {
//                    Log.e("OTP", "Invalid OTP")
//                }
//            }
//    }
}