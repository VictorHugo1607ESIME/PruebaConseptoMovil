package com.example.pruebaconceptomovil.services.Interfaces

import com.example.pruebaconceptomovil.constants.Constants
import com.example.pruebaconceptomovil.models.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Service {

    @GET(Constants.API)
    fun getImageData(
        @Query("api_key") apiKey: String
    ): Call<ImageResponse>

}