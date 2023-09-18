package com.example.pruebaconceptomovil

import com.example.pruebaconceptomovil.constants.Constants
import com.example.pruebaconceptomovil.models.ImageResponse
import com.example.pruebaconceptomovil.services.CallServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository() {

    private val _getImageStatus = MutableStateFlow(InfoImageStatus())
    val getImageStatus: StateFlow<InfoImageStatus> = _getImageStatus.asStateFlow()

    data class InfoImageStatus(
        var infoImage: ImageResponse = ImageResponse(),
        var status: String = "",
        var message: String = ""
    )

    suspend fun getInfoImage(){
        _getImageStatus.update { it.copy(status = "Load")
        }
        withContext(Dispatchers.IO){
            CallServices.callService().getImageData(Constants.API_KEY).enqueue(object :
                Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.code() == 200) {
                            _getImageStatus.update {
                                it.copy(
                                    infoImage = response.body() as ImageResponse,
                                    status = "Success"
                                )
                            }
                    } else {
                        _getImageStatus.update {
                            it.copy(
                                status = "Error",
                                message = response.message()
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    _getImageStatus.update {
                        it.copy(
                            status = "Error",
                            message = t.message.toString()
                        )
                    }
                }
            })
        }
    }

}