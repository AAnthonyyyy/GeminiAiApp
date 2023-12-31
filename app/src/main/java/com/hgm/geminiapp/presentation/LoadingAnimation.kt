package com.hgm.geminiapp.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * @author：HGM
 * @created：2023/12/29 0029
 * @description：加载动画组件
 **/
@Composable
fun LoadingAnimation(
      circleSize: Dp = 20.dp,
      spaceBetween: Dp = 8.dp,
      travelDistance: Dp = 20.dp,
      modifier: Modifier = Modifier,
      circleColor: Color = MaterialTheme.colorScheme.primary
) {
      // 三个圆的默认动画
      val circles = listOf(
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) }
      )
      // 三个圆的值
      val circleValues = circles.map { it.value }
      // 移动距离
      val distance = with(LocalDensity.current) { travelDistance.toPx() }


      circles.forEachIndexed { index, animatable ->
            LaunchedEffect(key1 = animatable) {
                  delay(index * 100L)
                  animatable.animateTo(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                              animation = keyframes {
                                    durationMillis = 1200
                                    0.0f at 0 with LinearOutSlowInEasing
                                    1.0f at 300 with LinearOutSlowInEasing
                                    0.0f at 600 with LinearOutSlowInEasing
                                    0.0f at 1200 with LinearOutSlowInEasing
                              },
                              repeatMode = RepeatMode.Restart
                        )
                  )
            }
      }

      Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
      ) {
            circleValues.forEachIndexed { index, value ->
                  Box(
                        modifier = Modifier
                              .size(circleSize)
                              .graphicsLayer {
                                    translationY = -value * distance
                              }
                              .background(
                                    color=circleColor,
                                    shape = CircleShape
                              )
                  )
            }
      }
}