package com.hmwn.headlinenewsmaker.view.template

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.ui.theme.black1
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.view.components.ToolbarView

@Composable
fun TemplateView() {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CnnNews() {

    Surface(shadowElevation = 3.dp) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.logo_tribunnews),
                        contentDescription = "My Image",
                        modifier = Modifier
                            .height(36.dp)

                    )
                }
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = black1,
                    contentDescription = "Back Button"
                )
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.White,
                titleContentColor = black1
            ),
        )
    }

}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewTemplateView() {
    CnnNews()
}