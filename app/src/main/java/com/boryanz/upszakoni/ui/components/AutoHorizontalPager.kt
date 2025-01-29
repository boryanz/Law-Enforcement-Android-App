package com.boryanz.upszakoni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.screens.bonussalary.dashboard.BonusSalaryDashboardUiState
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.BaseContent1
import kotlinx.coroutines.delay

@Composable
fun AutoAdvancePager(uiState: BonusSalaryDashboardUiState) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(pageCount = { uiState.sliderState?.size ?: 0 })
        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

        val pageInteractionSource = remember { MutableInteractionSource() }
        val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

        val autoAdvance = !pagerIsDragged && !pageIsPressed

        if (autoAdvance) {
            LaunchedEffect(pagerState, pageInteractionSource) {
                while (true) {
                    delay(3000)
                    val nextPage = (pagerState.currentPage + 1) % uiState.sliderState?.size!!
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
        ) { page ->
            PagerContent(
                uiState.sliderState?.get(page)?.value,
                uiState.sliderState?.get(page)?.progress
            )
        }

        Spacer.Vertical(8.dp)
        PagerIndicator(uiState.sliderState?.size!!, pagerState.currentPage)
    }
}


@Composable
fun PagerContent(description: String?, progress: Float?) {
    if (description == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically)
            .border(width = 1.dp, shape = RoundedCornerShape(4.dp), color = BaseContent)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = description,
            textAlign = TextAlign.Start
        )

        progress?.let {
            Spacer.Vertical(4.dp)
            LinearProgressIndicator(
                progress = { it },
                color = BaseContent1,
                trackColor = Color.LightGray,
                strokeCap = StrokeCap.Square,
                gapSize = 0.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color = if (currentPageIndex == iteration) Color.LightGray else BaseContent1
                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
    }
}