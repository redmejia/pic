package com.binarystack01.pic.presentation.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(
    navController: NavHostController,
) {

    val items = listOf(
        BottomBarItem.Camera,
        BottomBarItem.Gallery
    )

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.Black,
    ) {

        val selectedColor = Color(0xFF788EDE)
        val unselectedColor = Color(0xFFB8C3E8)

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = if (currentRoute == screen.route) screen.selected else screen.unselected),
                        tint = if (currentRoute == screen.route) selectedColor else unselectedColor,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        fontWeight = FontWeight.SemiBold,
                        color = if (currentRoute == screen.route) selectedColor else unselectedColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Black
                )
            )
        }

    }

}