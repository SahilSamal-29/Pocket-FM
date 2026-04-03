package com.example.pocketfm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketfm.ui.DetailActivity
import com.example.pocketfm.R
import com.example.pocketfm.databinding.ActivityHomeBinding
import com.example.pocketfm.viewmodel.AudioViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var trendingAdapter: HorizontalAdapter
    private lateinit var popularAdapter: HorizontalAdapter
    private lateinit var viewModel: AudioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, DetailActivity::class.java)
        trendingAdapter = HorizontalAdapter { item ->
            // click → open detail
            intent.putExtra("title", item.title)
            intent.putExtra("image", item.image)
            intent.putExtra("audio", item.audioUrl)
            intent.putExtra("desc", item.description)
            Log.d("DEBUG_ADAPTER", "desc = ${item.description}")
            startActivity(intent)
        }
        popularAdapter = HorizontalAdapter { item ->
            // click → open detail
            intent.putExtra("title", item.title)
            intent.putExtra("image", item.image)
            intent.putExtra("audio", item.audioUrl)
            intent.putExtra("desc", item.description)
            startActivity(intent)
        }


        binding.rvTrending.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrending.adapter = trendingAdapter

        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = popularAdapter

        viewModel = ViewModelProvider(this)[AudioViewModel::class.java]

        viewModel.fetchAudios()

        viewModel.audioList.observe(this) { list ->
            trendingAdapter.submitList(list)
            popularAdapter.submitList(list)
        }
    }
}