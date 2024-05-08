package com.example.hellojetpackcompose.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var searchQuery = mutableStateOf("")
    var names = mutableStateOf(listOf<String>())

    fun updateNames(newNames: List<String>) {
        names.value = newNames
    }
}

