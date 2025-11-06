package com.practicum.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.playlistmaker.ui.components.common.CustomTopBar
import com.practicum.playlistmaker.ui.components.search.SearchBarCustom
import com.practicum.playlistmaker.ui.models.SearchViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme


class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                SearchScreen()
            }
        }
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, name = "search-preview")
@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CustomTopBar(
                text = "Поиск",
                onBackClick = { },
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            SearchBarCustom(
                query = viewModel.query,
                onQueryChange = viewModel::onQueryChange,
                onClearClick = viewModel::clearQuery,
            )
        }
    }
}