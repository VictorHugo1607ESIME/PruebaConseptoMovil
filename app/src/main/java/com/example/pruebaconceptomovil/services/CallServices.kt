package com.example.pruebaconceptomovil.services

import com.example.pruebaconceptomovil.services.Interfaces.Service
object CallServices {

    fun callService() = RetrofitCliente.retrofitClient().create(
        Service::class.java
    )

}