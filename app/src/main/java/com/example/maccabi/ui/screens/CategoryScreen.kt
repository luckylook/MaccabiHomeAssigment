package com.example.maccabi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.maccabi.R
import com.example.maccabi.data.domain.Category
import com.example.maccabi.navigation.AppRoutes
import com.example.maccabi.ui.AppLoader
import com.example.maccabi.ui.ErrorUI
import com.example.maccabi.ui.TopBar
import com.example.maccabi.ui.viewmodels.CatalogViewModel

@Composable
fun CategoryScreen(navController: NavController, viewModel: CatalogViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(stringResource(id = R.string.all_categories))
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (uiState.isError) {
                ErrorUI(message = uiState.errorMessage!!)
            } else {
                LazyColumn(content = {
                    itemsIndexed(uiState.categories) { _, category ->
                        ColumnItem(category) {
                            viewModel.setSelectedCategory(category)
                            navController.navigate(AppRoutes.PRODUCTS)
                        }
                    }
                }
                )
            }

            if (uiState.isLoading) {
                AppLoader()
            }
        }
    }
}

@Composable
fun ColumnItem(
    category: Category,
    onItemClicked: (Category) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,

            ),
        shape = RectangleShape,
        modifier = Modifier
            .clickable {
                onItemClicked(category)
            }
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(category.thumbnail),
                contentDescription = stringResource(id = R.string.category_thumbnail_description),
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = category.name,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "${stringResource(id = R.string.products_label)} ${category.totalProducts}",
                    fontSize = 10.sp
                )
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "${stringResource(id = R.string.products_label)} ${category.totalStockProducts}",
                    fontSize = 10.sp
                )
            }
        }
    }
}

