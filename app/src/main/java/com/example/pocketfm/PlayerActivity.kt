package com.example.pocketfm

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.example.pocketfm.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val audioUrl = intent.getStringExtra("audio")
        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")
        Glide.with(this)
            .load(image)   // ✅ NOT item.image
            .into(binding.imgCover)

        binding.tvTitle.text = title

        setupPlayer(audioUrl)
        setupControls()

    }

    private fun setupPlayer(audioUrl: String?) {
        if (audioUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid audio", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        player = ExoPlayer.Builder(this).build()

        val mediaItem = MediaItem.fromUri(audioUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
    }

    private fun setupControls() {

        // Play/Pause toggle
        binding.btnPlayPause.setOnClickListener {
            player?.let {
                if (it.isPlaying) {
                    it.pause()
                    binding.btnPlayPause.text = "Play"
                } else {
                    it.play()
                    binding.btnPlayPause.text = "Pause"
                }
            }
        }

        // Seekbar updater
        handler.post(object : Runnable {
            override fun run() {
                player?.let { p ->
                    val current = p.currentPosition
                    val duration = p.duration

                    if (duration > 0) {
                        binding.seekBar.max = duration.toInt()
                        binding.seekBar.progress = current.toInt()

                        binding.tvTime.text =
                            "${formatTime(current)} / ${formatTime(duration)}"
                    }
                }
                handler.postDelayed(this, 1000)
            }
        })

        // Seekbar interaction
        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    player?.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format("%02d:%02d", min, sec)
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
        handler.removeCallbacksAndMessages(null)
    }
}