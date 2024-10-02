package com.aleksandrgenrikhs.myipaddress.presentation

import androidx.compose.runtime.Immutable

data class MyIpScreenUiState(
    val ip: MyIpUiState,
)

sealed interface MyIpUiState {

    data object Loading : MyIpUiState
    data object Error : MyIpUiState

    @Immutable
    data class Content(
        val myIP: String,
    ) : MyIpUiState
}