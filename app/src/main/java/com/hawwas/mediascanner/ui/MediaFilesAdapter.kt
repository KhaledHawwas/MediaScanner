package com.hawwas.mediascanner.ui

import android.view.*
import androidx.recyclerview.widget.*
import com.hawwas.mediascanner.*
import com.hawwas.mediascanner.databinding.*
import com.hawwas.ulibrary.ui.*

class MediaFilesAdapter: RecyclerView.Adapter<MediaFilesAdapter.ViewHolder>() {
    private var audioFiles = listOf<AudioFile>()
    fun setAudioFiles(audioFiles: List<AudioFile>) {
        this.audioFiles = audioFiles
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AudioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(audioFiles[position])
    }
    override fun getItemCount(): Int {
        return audioFiles.size
    }
    class ViewHolder(private val binding: AudioItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(audioFile: AudioFile) {
            binding.nameTv.text = audioFile.name
            binding.sizeTv.text = getSize( audioFile.size)
        }
    }
}