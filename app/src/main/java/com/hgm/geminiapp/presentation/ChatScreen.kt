package com.hgm.geminiapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
      state: UIState,
      onCloseClick: () -> Unit,
      onSendMessage: (String) -> Unit
) {
      var question by remember {
            mutableStateOf("")
      }
      val scrollState = rememberLazyListState()
      LaunchedEffect(key1 = state.messages.size){
            scrollState.animateScrollToItem(scrollState.layoutInfo.totalItemsCount)
      }

      Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                  TopAppBar(
                        title = { Text(text = "Gemini AI App") },
                        actions = {
                              IconButton(onClick = onCloseClick) {
                                    Icon(
                                          imageVector = Icons.Default.Close,
                                          contentDescription = null
                                    )
                              }
                        }
                  )
            }
      ) {
            Column(
                  modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
            ) {
                  LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                              .fillMaxSize()
                              .padding(horizontal = 12.dp)
                              .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                  ) {
                        items(state.messages) { message ->
                              Column(modifier = Modifier.fillMaxWidth()) {
                                    ChatMessage(
                                          message = message,
                                          modifier = Modifier.align(if (message.isFromLocal) Alignment.End else Alignment.Start)
                                    )
                              }
                        }
                  }

                  Row(
                        modifier = Modifier
                              .fillMaxWidth()
                              .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                  ) {
                        OutlinedTextField(
                              value = question,
                              onValueChange = { value ->
                                    question = value
                              },
                              modifier = Modifier.weight(1f)
                        )
                        if (state.isLoading){
                              CircularProgressIndicator()
                        }else{
                              IconButton(onClick = {
                                    onSendMessage(question)
                              }) {
                                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                              }
                        }
                  }
            }
      }
}