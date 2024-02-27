package com.example.maccabi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maccabi.R

@Composable
fun ErrorUI(message: String) {
    Text(text = message, color = Color.Red, textAlign = TextAlign.Center)
}

@Composable
fun AppLoader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .pointerInput(Unit) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.purple_500)
        )
    }
}

@Composable
fun TopBar(title: String, onBackClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .background(color = colorResource(id = R.color.purple_500))
            .fillMaxWidth()
            .height(60.dp)

    ) {
        if (onBackClick != null) {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight(),
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    tint = Color.White,
                    contentDescription = "Back"
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = if (onBackClick == null) 0.dp else 30.dp)
                .fillMaxWidth(),
            text = title,
            fontSize = 24.sp,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
    }

}