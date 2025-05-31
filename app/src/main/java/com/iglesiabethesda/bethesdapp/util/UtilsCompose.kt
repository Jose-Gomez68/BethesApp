package com.iglesiabethesda.bethesdapp.util

import androidx.annotation.DrawableRes
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