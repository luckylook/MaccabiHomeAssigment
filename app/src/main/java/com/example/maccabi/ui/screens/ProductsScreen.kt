package com.example.maccabi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.maccabi.R
import com.example.maccabi.data.domain.Product
import com.example.maccabi.ui.ErrorUI
import com.example.maccabi.ui.TopBar
import com.example.maccabi.ui.viewmodels.CatalogViewModel

@Composable
fun ProductsScreen(navController: NavController, viewModel: CatalogViewModel) {
    val category by viewModel.categorySelected.observeAsState()

    Scaffold(
        topBar = {
            TopBar(category?.name ?: "") {
                navController.popBackStack()
            }
        }
    ) {

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (category == null) {
                ErrorUI(message = stringResource(id = R.string.no_products_selected))
            } else {
                LazyColumn(content = {
                    itemsIndexed(category!!.products) { _, product ->
                        ProductItem(product)
                    }
                })
            }

        }
    }
}

@Composable
fun ProductItem(product: Product) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .size(400.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                text = product.name,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.W500
            )

            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = stringResource(id = R.string.product_thumbnail_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
            BottomCardItem(product.quantity, product.getFormatPrice() + product.amountSymbol)
        }
    }
}

@Composable
fun BottomCardItem(quantity: Int, price: String) {
    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
        LabelItem(stringResource(id = R.string.price_label), price)
        Spacer(modifier = Modifier.weight(1f))
        LabelItem(stringResource(id = R.string.quantity_label), quantity.toString())
    }
}

@Composable
fun LabelItem(title: String, value: String) {
    Row(modifier = Modifier.padding(5.dp)) {
        Text(
            modifier = Modifier.padding(end = 1.dp),
            text = title,
            fontSize = 12.sp
        )
        Text(
            modifier = Modifier
                .widthIn(20.dp)
                .padding(end = 1.dp),
            text = value,
            fontSize = 12.sp
        )
    }
}


