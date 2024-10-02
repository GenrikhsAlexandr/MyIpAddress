package com.aleksandrgenrikhs.myipaddress.data

import retrofit2.http.GET

interface ApiService {
    @GET("d4e2bt6jba6cmiekqmsv")
    suspend fun getIp(): MyIPAddressResponse
}