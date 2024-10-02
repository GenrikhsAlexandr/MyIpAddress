package com.aleksandrgenrikhs.myipaddress.data

import com.aleksandrgenrikhs.myipaddress.domain.MyIPAddress
import com.aleksandrgenrikhs.myipaddress.domain.MyIPRepository
import javax.inject.Inject

class MyIPRepositoryImpl
@Inject constructor(
    private val service: ApiService,
) : MyIPRepository {
    override suspend fun getIpAddress(): Result<MyIPAddress> {
        val ip = runCatching {
            service.getIp().myip
        }
        return ip.map { myIp ->
            MyIPAddress(
                ip = myIp
            )
        }
    }
}