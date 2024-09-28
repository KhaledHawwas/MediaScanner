package com.hawwas.mediascanner

import android.net.*

interface AudioReader {
    fun getAllAudioFiles(): List<AudioFile>
    fun getAllAudioFromFolder(uri: Uri): List<AudioFile>
}