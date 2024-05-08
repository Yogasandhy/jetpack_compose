package com.example.hellojetpackcompose.ui

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class MainScreen {
    @Composable
    fun GreetingList(viewModel: SearchViewModel, images: List<Int>, descs: List<String>, navController: NavController) {
        val filteredNames = if (viewModel.searchQuery.value.isEmpty()) {
            viewModel.names.value
        } else {
            viewModel.names.value.filter { it.contains(viewModel.searchQuery.value, ignoreCase = true) }
        }
        Log.d("GreetingList", "Filtered names: $filteredNames")

        if (filteredNames.isNotEmpty() && images.size == viewModel.names.value.size && descs.size == viewModel.names.value.size) {
            LazyColumn {
                items(filteredNames.size) { index ->
                    val nameIndex = viewModel.names.value.indexOf(filteredNames[index])
                    Greeting(name = filteredNames[index], image = images[nameIndex], desc = descs[nameIndex], navController = navController)
                }
            }
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text("No Data :(")
            }
        }
    }

    @Composable
    fun Greeting(name: String, image: Int, desc: String, navController: NavController, modifier: Modifier = Modifier) {
        var isExpanded by remember { mutableStateOf(false) }
        val animatedSizeDp by animateDpAsState(
            targetValue = if (isExpanded) 120.dp else 80.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ), label = ""
        )
        Card (
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable {
                    navController.navigate("detail_screen/$name/$desc/$image")
                }
        )
        {
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "Gambar $name",
                    modifier = modifier.size(animatedSizeDp)
                )
                Spacer(modifier = modifier.width(8.dp))
                Column(modifier = modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = if (desc.length > 100) "${desc.substring(0, 100)}..." else desc,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = if (isExpanded) "Show less" else "Show more"
                    )
                }
            }
        }
    }
}