package com.example.pruebaconceptomovil.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageResponse (

    @SerializedName("copyright")        val copyright: String? = "",
    @SerializedName("date")             val date: String? = "",
    @SerializedName("explanation")      val explanation: String? = "",
    @SerializedName("hdurl")            val hdurl: String? = "",
    @SerializedName("mediaType")        val media_type: String? = "",
    @SerializedName("serviceVersion")   val service_version: String? = "",
    @SerializedName("title")            val title: String? = "",
    @SerializedName("url")              val url: String? = ""

): Serializable