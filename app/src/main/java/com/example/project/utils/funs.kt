package com.example.project.utils

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.project.domain.models.DomainModelCharacter

@Composable
fun WorkerWithImage(
    item: DomainModelCharacter,
    height: Dp
) {
    AsyncImage(
        model = item.image,
        contentDescription = null,
        modifier = Modifier.height(height),
        contentScale = ContentScale.Fit
    )
}