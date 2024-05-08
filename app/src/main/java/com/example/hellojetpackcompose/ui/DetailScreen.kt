package com.example.hellojetpackcompose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun DetailScreen(name: String?, desc: String?, image: Int?, favoriteList: MutableList<FavoriteItem>) {
        val isAlreadyFavorited = favoriteList.any { it.name == name && it.image == image && it.desc == desc }
        var isFavorited by remember { mutableStateOf(isAlreadyFavorited) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            image?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = "Gambar $name",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = desc ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            FloatingActionButton(onClick = {
                isFavorited = !isFavorited
                if (isFavorited) {
                    if (!isAlreadyFavorited) {
                        favoriteList.add(FavoriteItem(name ?: "", image ?: 0, desc ?: ""))
                    }
                } else {
                    favoriteList.remove(FavoriteItem(name ?: "", image ?: 0, desc ?: ""))
                }
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Add to Favorites",
                    tint = if (isFavorited) Color.Red else Color.Gray
                )
            }
        }
    }
}