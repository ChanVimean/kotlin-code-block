package com.example.kura.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.kura.R
import com.example.kura.data.model.Component
import com.example.kura.data.model.ComponentSection

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
fun ComponentCard(
    component: Component,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageName = component.sections
        .filterIsInstance<ComponentSection.ResultImage>()
        .firstOrNull()
        ?.assetPath
    val resId = remember(imageName) {
        val id = imageName?.let {
            context.resources.getIdentifier(it, "drawable", context.packageName)
        } ?: 0
        if (id != 0) id else R.drawable.placeholder
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Thumbnail
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(12.dp))

            // Component name (bold)
            Text(
                component.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                minLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Domain in accent color
            Text(
                component.domain.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}