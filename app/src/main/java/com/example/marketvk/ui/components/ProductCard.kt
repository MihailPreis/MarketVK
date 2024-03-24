package com.example.marketvk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marketvk.data.ProductModel
import com.example.marketvk.ui.theme.MarketVKTheme


@Composable
fun ProductCard(
    model: ProductModel,
    onTap: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 5.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(Color.White)
            .clickable { onTap() }
    ) {
        AsyncImage(
            model = model.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .background(Color.LightGray)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 8.dp).padding(bottom = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {
                Text(text = "${model.price}$")
                Spacer(modifier = Modifier.wrapContentWidth())
                Row {
                    Icon(Icons.Filled.Star, contentDescription = null)
                    Text(text = "${model.rating}")
                }
            }

            Text(
                text = model.title,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(5f, TextUnitType.Em),
                maxLines = 2,
                minLines = 2
            )

            CategoryComponent(model.category)
        }
    }
}

@Preview
@Composable
fun PreviewProductCard() {
    MarketVKTheme {
        Surface {
            ProductCard(
                model = ProductModel(
                    id = 1,
                    title = "Text",
                    description = "Some text",
                    price = 112,
                    discountPercentage = 1.2,
                    rating = 4.12,
                    stock = 10,
                    brand = "Lui hui",
                    category = "test",
                    thumbnail = "https://images.unsplash.com/photo-1612528443702-f6741f70a049",
                    images = arrayListOf()
                ),
                onTap = {}
            )
        }
    }
}