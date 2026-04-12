package com.example.pocketfm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketfm.databinding.FragmentHomeBinding
import com.example.pocketfm.viewmodel.AudioViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AudioViewModel by viewModels()

    private lateinit var trendingAdapter: HorizontalAdapter
    private lateinit var popularAdapter: HorizontalAdapter
    private lateinit var newReleasesAdapter: HorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeData()

        viewModel.fetchAudios()  // 🔥 API call
    }

    // 🔧 Setup RecyclerViews
    private fun setupRecyclerViews() {

        val intent = Intent(requireContext(), DetailActivity::class.java)
        trendingAdapter = HorizontalAdapter{item ->
        // click → open detail
        intent.putExtra("title", item.title)
        intent.putExtra("image", item.image)
        intent.putExtra("audio", item.audioUrl)
        intent.putExtra("desc", item.description)
        Log.d("DEBUG_ADAPTER", "desc = ${item.description}")
        startActivity(intent)
        }
        popularAdapter = HorizontalAdapter{item ->
            // click → open detail
            intent.putExtra("title", item.title)
            intent.putExtra("image", item.image)
            intent.putExtra("audio", item.audioUrl)
            intent.putExtra("desc", item.description)
            startActivity(intent)
        }
        newReleasesAdapter = HorizontalAdapter{item ->
            // click → open detail
            intent.putExtra("title", item.title)
            intent.putExtra("image", item.image)
            intent.putExtra("audio", item.audioUrl)
            intent.putExtra("desc", item.description)
            startActivity(intent)
        }

        binding.rvTrending.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
        binding.rvNewReleases.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = newReleasesAdapter
        }
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
    }

    // 👀 Observe ViewModel
    private fun observeData() {

        viewModel.audioList.observe(viewLifecycleOwner) { list ->

            // Split or reuse list
            trendingAdapter.submitList(list)
            popularAdapter.submitList(list.shuffled())
            newReleasesAdapter.submitList(list.shuffled())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}