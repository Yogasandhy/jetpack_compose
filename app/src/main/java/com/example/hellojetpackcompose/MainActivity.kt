package com.example.hellojetpackcompose

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hellojetpackcompose.ui.AboutScreen
import com.example.hellojetpackcompose.ui.Data.Companion.sampleDesc
import com.example.hellojetpackcompose.ui.Data.Companion.sampleImage
import com.example.hellojetpackcompose.ui.Data.Companion.sampleName
import com.example.hellojetpackcompose.ui.DetailScreen
import com.example.hellojetpackcompose.ui.FavoriteItem
import com.example.hellojetpackcompose.ui.FavoriteScreen
import com.example.hellojetpackcompose.ui.MainScreen
import com.example.hellojetpackcompose.ui.SearchScreen
import com.example.hellojetpackcompose.ui.SearchViewModel
import com.example.hellojetpackcompose.ui.theme.HelloJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloJetpackComposeTheme {
                NavGraph()
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HelloJetpackComposeAppPreview() {
    HelloJetpackComposeTheme {
        NavGraph()
    }
}

@Composable
fun HelloJetpackComposeApp(searchViewModel: SearchViewModel, navController: NavController) {
    val mainScreen = MainScreen()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        mainScreen.GreetingList(searchViewModel, sampleImage, sampleDesc, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("Language Programming") },
        actions = {
            IconButton(onClick = { navController.navigate("about") }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
            IconButton(onClick = { navController.navigate("favorite") }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite")
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val favoriteList = remember { mutableStateListOf<FavoriteItem>() }
    val searchViewModel: SearchViewModel = viewModel()
    searchViewModel.names.value = sampleName

    val searchScreen = SearchScreen()
    val favoriteScreen = FavoriteScreen()
    val detailScreen = DetailScreen()
    val aboutScreen = AboutScreen()

    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            Scaffold(
                topBar = { MyAppBar(navController) }
            ) {
                Column {
                    Box(modifier = Modifier.padding(top = 70.dp)) {
                        searchScreen.Search(searchViewModel)
                    }
                    Box(modifier = Modifier.padding(top = 10.dp)) {
                        HelloJetpackComposeApp(searchViewModel, navController)
                    }
                }
            }
        }
        composable("detail_screen/{name}/{desc}/{image}") { backStackEntry ->
            val arguments = backStackEntry.arguments
            detailScreen.DetailScreen(
                name = arguments?.getString("name"),
                desc = arguments?.getString("desc"),
                image = arguments?.getString("image")?.toIntOrNull(),
                favoriteList = favoriteList
            )
        }
        composable("about") {
            aboutScreen.AboutScreen()
        }
        composable("favorite") {
            favoriteScreen.FavoriteList(favorites = favoriteList, navController = navController)
        }
    }
}


