package com.hgm.geminiapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.hgm.geminiapp.BuildConfig
import com.hgm.geminiapp.domain.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class ChatViewModel : ViewModel() {

      private val _uiState = MutableStateFlow(UIState())
      val uiState = _uiState.asStateFlow()

      private var generativeModel: GenerativeModel

      init {
            val config = generationConfig {
                  temperature = 0.70f
            }

            // 初始化生成模型
            generativeModel = GenerativeModel(
                  modelName = "gemini-pro",
                  apiKey = BuildConfig.apiKey,
                  generationConfig = config
            )
      }


      fun sendMessage(question: String) {
            _uiState.update {
                  it.copy(isLoading = true)
            }

            _uiState.update {
                  it.copy(
                        isLoading = true,
                        messages = uiState.value.messages + Message(
                              isFromLocal = true,
                              content = question
                        )
                  )
            }

            viewModelScope.launch(Dispatchers.IO) {
                  try {
                        generativeModel.generateContentStream(question).collect { result ->
                              val message = Message(
                                    content = result.text ?: "Unknown response",
                                    isFromLocal = false
                              )
                              _uiState.update {
                                    it.copy(
                                          isLoading = false,
                                          messages = uiState.value.messages + message
                                    )
                              }
                        }
                  } catch (e: IOException) {
                        e.printStackTrace()
                        _uiState.update {
                              it.copy(isLoading = false)
                        }
                  }
            }
      }
}

data class UIState(
      val isLoading: Boolean = false,
      val messages: List<Message> = emptyList()
)