package com.example.marketvk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marketvk.ui.gen


@Composable
fun CategoryComponent(
    title: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        color = Color.gen(title, true),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .composed { modifier }
            .background(
                Color.gen(
                    title,
                    false,
                    if (isSelected) 0.5f else 1f
                )
            )
            .padding(vertical = 5.dp, horizontal = 10.dp)
    )
}