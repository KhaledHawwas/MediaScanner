package com.hawwas.mediascanner.ui

import android.app.*
import android.content.*
import android.os.*
import android.util.*
import android.widget.Toast
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.app.*
import androidx.core.view.*
import androidx.recyclerview.widget.*
import com.hawwas.mediascanner.R
import com.hawwas.mediascanner.databinding.*

class MainActivity: AppCompatActivity() {
    lateinit var mainActivityBinding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        val permissions = if (Build.VERSION.SDK_INT >= 33) {
            arrayOf(
                android.Manifest.permission.READ_MEDIA_AUDIO,
            )
        } else {
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        val adapter = MediaFilesAdapter()
        mainActivityBinding.filesRv.adapter = adapter
        mainActivityBinding.filesRv.layoutManager = LinearLayoutManager(this)
        viewModel.audioFiles.observe(this) {
            adapter.setAudioFiles(it)
        }
        mainActivityBinding.readExternalBtn.setOnClickListener {
            ActivityCompat.requestPermissions(this, permissions, 0)
            viewModel.getAllAudioFiles()
        }
        mainActivityBinding.chooseFileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            startActivityForResult(intent, 0)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data?.data == null) {
                Log.e("MainActivity", "No data")
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.getAllAudioFromFolder(data.data!!)
        }
    }
}