package com.binarystack01.pic.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binarystack01.pic.presentation.ui.screens.camera.Camera
import com.binarystack01.pic.presentation.ui.screens.gallery.PicGallery


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppScreens.Camera.name
    ) {

        composable(route = AppScreens.Camera.name) {
            Camera(navController = navController)
        }

        composable(route = AppScreens.Gallery.name) {
            PicGallery(navController = navController)
        }
    }

}

