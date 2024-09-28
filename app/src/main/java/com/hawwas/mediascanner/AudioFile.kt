package com.hawwas.mediascanner

import android.net.Uri

data class AudioFile(
    val uri : Uri,
    val name : String,
    val size:Long,
)
