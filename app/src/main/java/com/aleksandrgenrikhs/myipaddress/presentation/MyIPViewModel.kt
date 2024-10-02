package com.aleksandrgenrikhs.myipaddress.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.myipaddress.domain.MyIPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyIPViewModel
@Inject constructor(
    private val repository: MyIPRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyIpScreenUiState> = MutableStateFlow(
        MyIpScreenUiState(ip = MyIpUiState.Loading)
    )
    val uiState: StateFlow<MyIpScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMyIp()
        }
    }

    private suspend fun getMyIp() = repository.getIpAddress()
        .map { myIP ->
            MyIpUiState.Content(
                myIP = myIP.ip
            )
        }
        .onFailure {
            Exception().message
        }
        .recover { MyIpUiState.Error }
        .onSuccess { newState ->
            _uiState.update { state ->
                state.copy(ip = newState)
            }
        }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(ip = MyIpUiState.Loading)
            }
            getMyIp()
        }
    }
}