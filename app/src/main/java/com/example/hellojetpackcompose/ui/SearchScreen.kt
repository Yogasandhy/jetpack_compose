package com.example.hellojetpackcompose.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SearchScreen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Search(viewModel: SearchViewModel, modifier: Modifier = Modifier) {
        SearchBar(
            query = viewModel.searchQuery.value,
            onQueryChange = {
                viewModel.searchQuery.value = it
                Log.d("Search", "Query changed: $it")
            },
            onSearch = {
                viewModel.updateNames(viewModel.names.value.filter { it.contains(viewModel.searchQuery.value, ignoreCase = true) })
            },
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            placeholder = {
                Text("Search")
            },
            shape = MaterialTheme.shapes.large,
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
        }
    }
}