package com.iglesiabethesda.bethesdapp.util

import androidx.annotation.DrawableRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
