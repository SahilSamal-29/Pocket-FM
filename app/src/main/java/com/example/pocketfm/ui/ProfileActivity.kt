package com.example.pocketfm.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pocketfm.R
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvName = findViewById<TextView>(R.id.tvUserName)
        val tvEmail = findViewById<TextView>(R.id.tvUserEmail)
        val user = FirebaseAuth.getInstance().currentUser
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val tvAvatarInitials = findViewById<TextView>(R.id.tvAvatarInitials)

        if (user != null) {

            val name = user.displayName ?: "No Name"
            val email = user.email ?: "No Email"

            tvName.text = name
            tvEmail.text = email
            tvAvatarInitials.text = getInitials(name)

        } else {
            // User not logged in
            tvName.text = "Guest"
            tvEmail.text = ""
        }
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    fun getInitials(name: String?): String {

        if (name.isNullOrBlank()) return "?"

        val words = name.trim().split("\\s+".toRegex())

        return when (words.size) {
            1 -> words[0].first().uppercase()
            else -> {
                val first = words[0].first()
                val last = words[words.size - 1].first()
                "$first$last".uppercase()
            }
        }
    }
}