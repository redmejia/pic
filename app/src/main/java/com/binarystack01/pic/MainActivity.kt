package com.binarystack01.pic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.binarystack01.pic.presentation.ui.navigation.AppNavigation
import com.binarystack01.pic.presentation.viewmodels.PermissionsViewModel
import com.binarystack01.pic.ui.theme.PicTheme

class MainActivity : ComponentActivity() {

    private lateinit var permissionsViewModel: PermissionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PicTheme {

                permissionsViewModel = viewModel<PermissionsViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
