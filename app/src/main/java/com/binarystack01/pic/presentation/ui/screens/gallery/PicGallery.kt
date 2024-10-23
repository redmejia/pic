package com.binarystack01.pic.presentation.ui.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.binarystack01.pic.presentation.ui.navigation.AppScreens

@Composable
fun PicGallery(
    navController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(route = AppScreens.Camera.name) }) {
            Text(text = "Back to camera")
        }
    }
}

@Preview
@Composable
fun PicGalleryPreview() {
//    PicGallery()
}
