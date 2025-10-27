package com.example.project.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.utils.WorkerWithImage

@Composable
fun CharacterItem(
    item: DomainModelCharacter,
    onClick: () -> Unit
) {
    Card(
        Modifier
            .wrapContentSize()
            .padding(5.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.width(180.dp)
        ) {
            Box(
                modifier = Modifier.width(180.dp)
            ) {
                WorkerWithImage(item, 180.dp)
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 7.dp),
                    text = item.status,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.width(180.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                Modifier.width(180.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.gender,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.padding(horizontal = 10.dp))
                Text(
                    text = item.species,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}