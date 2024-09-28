package com.hawwas.mediascanner

import android.content.*
import android.net.*
import android.provider.*
import androidx.documentfile.provider.*

class AudioReaderImpl(private val context: Context): AudioReader {
    override fun getAllAudioFiles(): List<AudioFile> {
        val audioFiles = mutableListOf<AudioFile>()
        val queryUri = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.MIME_TYPE,
        )
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("audio/%")
        context.contentResolver.query(
            queryUri,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val nameColumn =
                cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val sizeColumn =
                cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
            val typeColumn =
                cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getLong(sizeColumn)
                val type = cursor.getString(typeColumn)
                val contentUri = ContentUris.withAppendedId(queryUri, id)
                if (name == null || type == null) {
                    continue
                }
                val audioFile = AudioFile(contentUri, name, size)
                println(audioFile)
                audioFiles.add(audioFile)
            }
        }
        return audioFiles
    }

    override fun getAllAudioFromFolder(uri: Uri): List<AudioFile> {
        val audioFiles = mutableListOf<AudioFile>()
        val documentUri = DocumentsContract.buildDocumentUriUsingTree(
            uri,
            DocumentsContract.getTreeDocumentId(uri)
        )
        val documentFile = DocumentFile.fromTreeUri(context, documentUri)

        documentFile?.listFiles()?.forEach { file ->
            if (file.isFile && file.type?.startsWith("audio/") == true) {
                val audioFile = AudioFile(file.uri, file.name ?: "Unknown", file.length())
                println(audioFile)
                audioFiles.add(audioFile)
            }
        }
        return audioFiles
    }
}