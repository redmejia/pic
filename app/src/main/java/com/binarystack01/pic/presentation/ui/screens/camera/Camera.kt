package com.binarystack01.pic.presentation.ui.screens.camera

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.binarystack01.pic.presentation.ui.components.actionbutton.CaptureButton
import com.binarystack01.pic.presentation.viewmodels.CaptureViewModel
import com.binarystack01.pic.presentation.viewmodels.PermissionsViewModel
import kotlinx.coroutines.delay

@Composable
fun Camera(
    captureViewModel: CaptureViewModel,
    permissionsViewModel: PermissionsViewModel,
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
            Log.d("CAMERA", "Setting up camera use cases")
            cameraController.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            cameraController.bindToLifecycle(lifecycleOwner)
        }
        onDispose {
            Log.d("CAMERA", "Unbinding camera")
            cameraController.unbind()
        }
    }


    val clicked = remember {
        mutableStateOf(false)
    }

    val visibleBlink = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(visibleBlink.value) {
        if (visibleBlink.value) {
            delay(200)
            visibleBlink.value = false
        }
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
            BlinkAnimation(visible = visibleBlink.value)
            Box(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .matchParentSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CaptureButton(
                    onClick = {
                        clicked.value = !clicked.value
                        visibleBlink.value = true

                        captureViewModel.capturePicture(
                            context = context,
                            controller = cameraController
                        )
                    },
                    clicked = clicked
                )
            }
        }
    }
}

@Composable
fun BlinkAnimation(
    visible: Boolean,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            initialAlpha = 0.0f,
            animationSpec = tween(durationMillis = 300, easing = EaseIn)
        ),
        exit = fadeOut(
            targetAlpha = 0.0f,
            animationSpec = tween(durationMillis = 500, easing = EaseInOut)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x61000000))
        )
    }
}
