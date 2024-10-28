package com.binarystack01.pic.presentation.ui.screens.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binarystack01.pic.presentation.viewmodels.CaptureViewModel
import com.binarystack01.pic.R

@Composable
fun PicGallery(
    captureViewModel: CaptureViewModel,
) {

    val capturedImages by captureViewModel.captureState.collectAsState()

    if (capturedImages.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.photo_prints),
                contentDescription = "No Photo",
                modifier = Modifier.size(55.dp)
            )
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(capturedImages) { photo ->
                    Image(bitmap = photo.asImageBitmap(), contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Preview
@Composable
fun PicGalleryPreview() {
//    PicGallery()
}
