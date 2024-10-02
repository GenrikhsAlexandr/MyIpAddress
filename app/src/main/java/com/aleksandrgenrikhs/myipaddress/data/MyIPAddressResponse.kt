package com.aleksandrgenrikhs.myipaddress.data

import kotlinx.serialization.Serializable

@Serializable
data class MyIPAddressResponse(
    val myip: String
)
