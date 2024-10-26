package com.binarystack01.pic.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.binarystack01.pic.presentation.ui.screens.camera.Camera
import com.binarystack01.pic.presentation.ui.screens.gallery.PicGallery
import com.binarystack01.pic.presentation.viewmodels.CaptureViewModel
import com.binarystack01.pic.presentation.viewmodels.PermissionsViewModel


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    captureViewModel: CaptureViewModel,
    permissionsViewModel: PermissionsViewModel,
    navController: NavHostController,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppScreens.Camera.name
    ) {

        composable(route = AppScreens.Camera.name) {
            Camera(
                captureViewModel = captureViewModel,
                permissionsViewModel = permissionsViewModel,
            )
        }

        composable(route = AppScreens.Gallery.name) {
            PicGallery(
                captureViewModel = captureViewModel,
            )
        }
    }

}


