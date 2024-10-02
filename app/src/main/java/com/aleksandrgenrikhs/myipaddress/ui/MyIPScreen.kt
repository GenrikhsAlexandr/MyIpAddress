package com.aleksandrgenrikhs.myipaddress.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.myipaddress.R
import com.aleksandrgenrikhs.myipaddress.presentation.MyIPViewModel
import com.aleksandrgenrikhs.myipaddress.presentation.MyIpScreenUiState
import com.aleksandrgenrikhs.myipaddress.presentation.MyIpUiState
import com.aleksandrgenrikhs.myipaddress.ui.theme.light_blue
import com.aleksandrgenrikhs.myipaddress.ui.theme.red
import com.aleksandrgenrikhs.myipaddress.ui.theme.text

@Composable
fun MyIPScreen(
    viewModel: MyIPViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Content(
        uiState = uiState,
        onRefresh = viewModel::refresh,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: MyIpScreenUiState,
    onRefresh: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = light_blue,
                    titleContentColor = text,
                ),
                title = {
                    Text(stringResource(id = R.string.myIPAddress))
                },

                actions = {
                    IconButton(
                        onClick = {
                            (context as Activity).finish()
                        }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val state = uiState.ip) {
            is MyIpUiState.Loading -> {
                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(light_blue),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            is MyIpUiState.Error -> {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(light_blue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.error),
                        color = red,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = modifier.height(48.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = onRefresh
                        )
                        {
                            Text(
                                text = stringResource(id = R.string.refresh),
                                color = text,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            is MyIpUiState.Content -> {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(light_blue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.myIP,
                        color = text,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = modifier.height(48.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = onRefresh
                        )
                        {
                            Text(
                                text = stringResource(id = R.string.refresh),
                                color = text,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewContent(
    @PreviewParameter(IPUiStates::class)
    uiState: MyIpScreenUiState
) {
    Content(
        uiState = uiState,
        onRefresh = {},
    )
}

private class IPUiStates :
    PreviewParameterProvider<MyIpScreenUiState> {
    override val values: Sequence<MyIpScreenUiState>
        get() = sequenceOf(
            MyIpScreenUiState(
                ip = MyIpUiState.Loading
            ),
            MyIpScreenUiState(
                ip = MyIpUiState.Error
            ),
            MyIpScreenUiState(
                ip = MyIpUiState.Content(
                    myIP = "63.251.122.112"
                )
            )
        )
}