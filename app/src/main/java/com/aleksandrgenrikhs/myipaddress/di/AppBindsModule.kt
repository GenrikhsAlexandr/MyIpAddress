package com.aleksandrgenrikhs.myipaddress.di

import com.aleksandrgenrikhs.myipaddress.data.MyIPRepositoryImpl
import com.aleksandrgenrikhs.myipaddress.domain.MyIPRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppBindsModule {
    @Binds
    @Singleton
    fun bindRepository(impl: MyIPRepositoryImpl): MyIPRepository
}