package com.practicum.playlistmaker.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.components.common.CustomTopBar
import com.practicum.playlistmaker.ui.components.search.SearchBarCustom
import com.practicum.playlistmaker.ui.viewmodels.SearchViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary

@Preview(device = "id:pixel_5", showSystemUi = true, name = "search-preview")
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.search_title),
                onBackClick = onBackClick,
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