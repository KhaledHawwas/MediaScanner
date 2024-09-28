package com.hawwas.mediascanner.ui


import android.net.Uri
import androidx.lifecycle.*
import com.hawwas.mediascanner.*
import kotlinx.coroutines.*

class MainActivityViewModel: ViewModel() {
    private var job: Job? = null
    var audioFiles = MutableLiveData<List<AudioFile>>()
    fun getAllAudioFiles(){
        if (job?.isActive == true) {
            return
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            audioFiles.postValue(MyApp.audioReader.getAllAudioFiles())
        }
    }
    fun getAllAudioFromFolder(uri: Uri){
        if (job?.isActive == true) {
            return
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            audioFiles.postValue(MyApp.audioReader.getAllAudioFromFolder(uri))
        }
    }
}