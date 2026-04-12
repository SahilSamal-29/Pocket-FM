package com.example.pocketfm.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.example.pocketfm.R
import com.example.pocketfm.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private val SEEK_DURATION = 10_000L  // 10 seconds

    // Current playback speed
    private var currentSpeed = 1.0f

    // List of available speeds
    private val availableSpeeds = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val audioUrl = intent.getStringExtra("audio")
        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")

        Glide.with(this)
            .load(image)
            .fitCenter()
            .into(binding.imgCover)

        binding.tvTitle.text = title

        setupPlayer(audioUrl)
        setupControls()
        setupSpeedControls()


    }

    private fun updatePlayPauseIcon() {
        player?.let {
            if (it.isPlaying) {
                binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
            } else {
                binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            }
        }
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
        player?.play() // Auto play when opening player
    }

    private fun setupControls() {
        // Play/Pause
        binding.btnPlayPause.setOnClickListener {
            player?.let { player ->
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
                updatePlayPauseIcon()   // Update icon after state change
            }
        }

        // Seekbar updater
        handler.post(object : Runnable {
            override fun run() {
                player?.let { p ->
                    val current = p.currentPosition
                    val duration = p.duration

                    if (duration > 0) {
                        binding.seekbar.max = duration.toInt()
                        binding.seekbar.progress = current.toInt()
                        binding.tvTime.text = "${formatTime(current)} / ${formatTime(duration)}"
                    }
                }
                handler.postDelayed(this, 1000)
            }
        })

        // Seekbar listener
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player?.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Back 10s
        binding.btnBackward.setOnClickListener {
            player?.let {
                val newPosition = it.currentPosition - SEEK_DURATION
                it.seekTo(if (newPosition < 0) 0 else newPosition)
            }
        }

        // Forward 10s
        binding.btnForward.setOnClickListener {
            player?.let {
                val newPosition = it.currentPosition + SEEK_DURATION
                val duration = it.duration
                it.seekTo(if (newPosition > duration) duration else newPosition)
            }
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupSpeedControls() {
        val speedViews = listOf(
            binding.tvSpeed05 to 0.5f,
            binding.tvSpeed075 to 0.75f,
            binding.tvSpeed1 to 1.0f,
            binding.tvSpeed125 to 1.25f,
            binding.tvSpeed15 to 1.5f,
            binding.tvSpeed175 to 1.75f,
            binding.tvSpeed2 to 2.0f
        )

        speedViews.forEach { (textView, speed) ->
            textView.setOnClickListener {
                changePlaybackSpeed(speed)
                updateSpeedUI(speedViews, textView)
            }
        }

        // Set initial speed (1x)
        updateSpeedUI(speedViews, binding.tvSpeed1)
    }

    private fun changePlaybackSpeed(speed: Float) {
        currentSpeed = speed
        player?.playbackParameters = PlaybackParameters(speed)
    }

    private fun updateSpeedUI(
        speedViews: List<Pair<TextView, Float>>,
        selectedView: TextView
    ) {
        speedViews.forEach { (view, _) ->
            if (view == selectedView) {
                view.background = getDrawable(R.drawable.bg_speed_chip_selected)
                view.setTextColor(resources.getColor(android.R.color.white, null))
//                view.setTextStyle(TextView.BOLD)  // or use textStyle="bold" in XML
            } else {
                view.background = getDrawable(R.drawable.bg_speed_chip)
                view.setTextColor(resources.getColor(android.R.color.darker_gray, null)) // or your #888888
            }
        }
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