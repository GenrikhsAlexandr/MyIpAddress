package com.aleksandrgenrikhs.myipaddress.domain

interface MyIPRepository {
    suspend fun getIpAddress(): Result<MyIPAddress>
}