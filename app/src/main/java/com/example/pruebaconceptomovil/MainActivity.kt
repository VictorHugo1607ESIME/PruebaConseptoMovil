package com.example.pruebaconceptomovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pruebaconceptomovil.databinding.ActivityMainBinding
import com.example.pruebaconceptomovil.models.ImageResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dataImage: ImageResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initUI()
        observe()
        setContentView(binding.root)
    }

    private fun initUI() {
        mainViewModel = MainViewModel()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.getImageStatus.collect{
                when(it.status){
                    "Load" -> {
                        binding.progress.visibility = View.VISIBLE
                    }

                    "Success" -> {
                        dataImage = it.infoImage
                        loadImageData()
                        binding.progress.visibility = View.GONE
                    }

                    "Error" -> {
                        binding.progress.visibility = View.GONE
                        Toast.makeText(applicationContext, com.example.pruebaconceptomovil.constants.Constants.ERROR_LOAD_IMAGE, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loadImageData() {
        if (!dataImage.title.isNullOrEmpty())
            binding.tvTitleImage.text = dataImage.title
        else
            binding.tvTitleImage.text = ""

        Glide.with(this)
            .load(dataImage.hdurl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivImageNasa)

    }
}