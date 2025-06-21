package com.iglesiabethesda.bethesdapp.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder

@Composable
fun GifImageLocal(@DrawableRes drawableId: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    AsyncImage(
        model = drawableId,
        imageLoader = imageLoader,
        contentDescription = "GIF animado local",
        modifier = modifier
    )
}

@Composable
fun InitialsAvatar(
    fullName: String = "Jose Armando Gomez Zamora",
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF6200EE),
    textColor: Color = Color.White,
    fontSize: TextUnit = 20.sp
) {
    val initials = remember(fullName) {
        fullName.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercase() }
            .joinToString("")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(64.dp)
            .background(backgroundColor, shape = CircleShape)
    ) {
        Text(
            text = initials,
            color = textColor,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SimpleAlertDialog(
    title: String,
    message: String,
    buttonText: String = "Cerrar",
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = buttonText)
            }
        }
    )
}
