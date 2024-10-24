package com.binarystack01.pic.presentation.ui.screens.camera

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.binarystack01.pic.presentation.ui.components.actionbutton.CaptureButton
import com.binarystack01.pic.presentation.viewmodels.PermissionsViewModel


@Composable
fun Camera(
    permissionsViewModel: PermissionsViewModel = viewModel(),
) {


    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                permissionsViewModel.onCameraPermission(
                    permission = Manifest.permission.CAMERA,
                    isGranted = true
                )
            } else {
                // not need
                permissionsViewModel.onCameraPermission(
                    permission = Manifest.permission.CAMERA,
                    isGranted = false
                )
            }
        }
    )

    val permissionState by permissionsViewModel.permissionState.collectAsState()


    LaunchedEffect(permissionState) {
        Log.d("PERMISSION-STATE", "Camera: $permissionState")
        if (!permissionState) {
            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
        }
    }


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    val cameraController = remember { LifecycleCameraController(context) }

    DisposableEffect(permissionState) {
        if (permissionState) {
            cameraController.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            cameraController.bindToLifecycle(lifecycleOwner)
        }
        onDispose {
            cameraController.unbind()
        }
    }


    val clicked = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionState) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        controller = cameraController
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
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