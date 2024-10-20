package com.binarystack01.pic

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
                val permissionState by permissionsViewModel.permissionState.collectAsState()

                val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (isGranted) {
                            permissionsViewModel.onCameraPermission(
                                permission = Manifest.permission.CAMERA,
                                isGranted = true
                            )
                        } else {
                            permissionsViewModel.onCameraPermission(
                                permission = Manifest.permission.CAMERA,
                                isGranted = false
                            )
                        }
                    }
                )

                LaunchedEffect(Unit) {
                    if (!permissionsViewModel.checkForPermission(
                            applicationContext,
                            Manifest.permission.CAMERA
                        )
                    ) {
                        cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (permissionState) {
                            Text(text = "Ok Open Camera")
                        }
                        Button(onClick = {
                            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                        }) {
                            Text(text = "Open Camera")
                        }
                    }
                }
            }
        }
    }
}
