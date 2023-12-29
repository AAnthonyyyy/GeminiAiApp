package com.hgm.geminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hgm.geminiapp.presentation.ChatScreen
import com.hgm.geminiapp.presentation.ChatViewModel
import com.hgm.geminiapp.ui.theme.GeminiAiAppTheme

class MainActivity : ComponentActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                  GeminiAiAppTheme {
                        val viewModel by viewModels<ChatViewModel>()
                        val state by viewModel.uiState.collectAsState()
                        Surface(
                              modifier = Modifier.fillMaxSize(),
                              color = MaterialTheme.colorScheme.background
                        ) {
                              ChatScreen(
                                    state=state,
                                    onCloseClick = {},
                                    onSendMessage =viewModel::sendMessage
                              )
                        }
                  }
            }
      }
}
