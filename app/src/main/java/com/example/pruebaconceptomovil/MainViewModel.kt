package com.example.pruebaconceptomovil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pruebaconceptomovil.models.ImageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {

    private val mainRepository = MainRepository()
    private val _getImageStatus = MutableStateFlow(InfoImageStatus())
    val getImageStatus: StateFlow<InfoImageStatus> = _getImageStatus.asStateFlow()

    data class InfoImageStatus(
        var infoImage: ImageResponse = ImageResponse(),
        var status: String = "",
        var message: String = ""
    )

    init {
        observe()
        getImage()
        mainRepository.getImageStatus
    }

    fun getImage(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mainRepository.getInfoImage()
            }catch (e: Exception){
                _getImageStatus.update {
                    it.copy(
                        status = "Error",
                        message = e.toString()
                    )
                }
            }
        }
    }

    private fun observe() {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.getImageStatus.collect{response ->
                when(response.status){
                    "Load" ->{
                        _getImageStatus.update {
                            it.copy(
                                status = "Load"
                            )
                        }
                    }

                    "Success" ->{
                        _getImageStatus.update {
                            it.copy(
                                status = "Success",
                                infoImage = response.infoImage
                            )
                        }
                    }

                    "Error" ->{
                        _getImageStatus.update {
                            it.copy(
                                status = "Error"
                            )
                        }
                    }
                }
            }
        }
    }
}