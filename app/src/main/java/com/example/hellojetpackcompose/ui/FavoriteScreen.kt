package com.example.hellojetpackcompose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

class FavoriteScreen {
    @Composable
    fun FavoriteList(favorites: List<FavoriteItem>, navController: NavController) {
        val mainScreen = MainScreen()

        if (favorites.isNotEmpty()) {
            LazyColumn {
                items(favorites.size) { index ->
                    mainScreen.Greeting(
                        name = favorites[index].name,
                        image = favorites[index].image,
                        desc = favorites[index].desc,
                        navController = navController
                    )
                }
            }
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text("No favorites yet :(")
            }
        }
    }

}