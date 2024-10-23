package com.binarystack01.pic.presentation.ui.navigation

import com.binarystack01.pic.R

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val selected: Int,
    val unselected: Int,
) {
    object Camera : BottomBarItem(
        route = "Camera",
        title = "Camera",
        selected = R.drawable.camera_filled,
        unselected = R.drawable.camera_outline
    )

    object Gallery : BottomBarItem(
        route = "Gallery",
        title = "Gallery",
        selected = R.drawable.image_filled,
        unselected = R.drawable.image_outline
    )
}