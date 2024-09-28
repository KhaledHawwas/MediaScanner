package com.hawwas.mediascanner

import android.app.*

class MyApp: Application() {
    companion object{
    lateinit var audioReader: AudioReader
    }
    override fun onCreate() {
        super.onCreate()
        audioReader = AudioReaderImpl(this)
    }
}