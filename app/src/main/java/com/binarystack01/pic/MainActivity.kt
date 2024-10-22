package com.binarystack01.pic

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.binarystack01.pic.presentation.ui.components.actionbutton.CaptureButton
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

                val permissionState by permissionsViewModel.permissionState.collectAsState()

                // Request permission not granted yet
                LaunchedEffect(permissionState) {
                    if (!permissionState) {
                        cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                val clicked = remember {
                    mutableStateOf(false)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if (permissionState) {
                            Camera(modifier = Modifier.fillMaxSize())
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .matchParentSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                CaptureButton(
                                    onClick = {
                                        Log.d("CLICK", "onCreate: clicked")
                                        clicked.value = !clicked.value
                                    },
                                    clicked = clicked
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
