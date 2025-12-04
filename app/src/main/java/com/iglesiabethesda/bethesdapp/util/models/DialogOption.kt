package com.iglesiabethesda.bethesdapp.util.models

import androidx.compose.ui.graphics.Color


data class DialogOption(
    val label: String,
    val color: Color = Color.Black,
    val onClick: () -> Unit
)
