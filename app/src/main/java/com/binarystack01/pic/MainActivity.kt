package com.binarystack01.pic

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.binarystack01.pic.presentation.ui.screens.camera.Camera
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

//                LaunchedEffect(Unit) {
//                    if (!permissionsViewModel.checkForPermission(
//                            applicationContext,
//                            Manifest.permission.CAMERA
//                        )
//                    ) {
//                        cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
//                    }
//                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if (!permissionsViewModel.checkForPermission(
                                applicationContext,
                                Manifest.permission.CAMERA
                            )
                        ) {
                            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                        } else {
                            Camera(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
