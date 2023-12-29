package com.hgm.geminiapp.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hgm.geminiapp.domain.Message
import com.hgm.geminiapp.ui.theme.OldRose
import com.hgm.geminiapp.ui.theme.Purple80
import com.hgm.geminiapp.ui.theme.PurpleGrey80
import com.hgm.geminiapp.ui.theme.Vanilla


@Composable
fun ChatMessage(
      message: Message,
      modifier: Modifier = Modifier,
      roundedCornerSize: Dp = 20.dp
) {
      var expandedState by remember {
            mutableStateOf(false)
      }

      Column(
            modifier = modifier
                  .clip(
                        RoundedCornerShape(
                              topStart = if (message.isFromLocal) roundedCornerSize else 0.dp,
                              topEnd = roundedCornerSize,
                              bottomStart = roundedCornerSize,
                              bottomEnd = if (message.isFromLocal) 0.dp else roundedCornerSize
                        )
                  )
                  .background(if (message.isFromLocal) OldRose else Vanilla)
                  .clickable { expandedState = !expandedState }
                  .animateContentSize()
                  .padding(16.dp)
      ) {
            Text(
                  text = message.content,
                  fontSize = 16.sp,
                  color = Color.Black,
                  modifier = Modifier.widthIn(max = 270.dp),
                  maxLines = if (expandedState) 2 else Int.MAX_VALUE
            )
      }
}