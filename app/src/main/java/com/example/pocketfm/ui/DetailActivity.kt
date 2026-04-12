package com.example.pocketfm.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pocketfm.R
import com.example.pocketfm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupUI()
        setupClick()
//        binding.imgBack.setOnClickListener {
//            onBackPressedDispatcher.onBackPressed()
//        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun setupUI() {

        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")
        val audio = intent.getStringExtra("audio")
        val desc = intent.getStringExtra("desc")

        binding.tvTitle.text = title
//        binding.tvDescription.text = desc
        binding.tvDescription.text =
            if (desc.isNullOrEmpty()) "No description available"
            else Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY)
        Log.d("DEBUG_DESC", "desc = $desc")

        Glide.with(this)
            .load(image)
            .into(binding.imgBanner)
    }

    private fun setupClick() {

        val audio = intent.getStringExtra("audio")
        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        binding.btnPlay.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("image", image)
            intent.putExtra("audio", audio)
            intent.putExtra("title", title)
            startActivity(intent)
            startActivity(intent)
        }
    }
}