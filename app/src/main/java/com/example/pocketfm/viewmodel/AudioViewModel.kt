package com.example.pocketfm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketfm.repository.AudioRepository
import com.example.pocketfm.data.model.AudioItem
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {

    private val repo = AudioRepository()

    val audioList = MutableLiveData<List<AudioItem>>()

    fun fetchAudios() {
        viewModelScope.launch {
            try {
                val data = repo.getAudios()
                audioList.postValue(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}