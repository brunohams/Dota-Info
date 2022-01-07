package com.codingwithmitch.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codingwithmitch.core.domain.ProgressBarState
import com.codingwithmitch.core.domain.Queue
import com.codingwithmitch.core.domain.UIComponent

@Composable
fun DefaultScreenUI(
    messageQueue: Queue<UIComponent> = Queue(mutableListOf()),
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    onRemoveHeadFromQueue: () -> Unit,
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content()
            if (!messageQueue.isEmpty()) {
                messageQueue.peek()?.let {
                    if (it is UIComponent.Dialog) {
                        GenericDialog(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            title = it.title,
                            description = it.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue
                        )
                    }
                }
            }
            if (progressBarState is ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }
        }
    }
}