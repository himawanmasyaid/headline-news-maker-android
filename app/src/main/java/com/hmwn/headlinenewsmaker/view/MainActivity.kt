package com.hmwn.headlinenewsmaker.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.ui.theme.PrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.SoftPrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.black3
import com.hmwn.headlinenewsmaker.ui.theme.black5
import com.hmwn.headlinenewsmaker.ui.theme.body1
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.ui.theme.body2
import com.hmwn.headlinenewsmaker.ui.theme.body2Bold
import com.hmwn.headlinenewsmaker.ui.theme.body3
import com.hmwn.headlinenewsmaker.ui.theme.h4
import com.hmwn.headlinenewsmaker.ui.theme.h6
import com.hmwn.headlinenewsmaker.ui.theme.h7
import com.hmwn.headlinenewsmaker.ui.theme.h7Medium
import com.hmwn.headlinenewsmaker.view.card.HeadlineNewsCard
import com.hmwn.headlinenewsmaker.view.home.HomeView
import com.hmwn.headlinenewsmaker.view.template.CnnNews

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                InitView()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun InitView() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBarView()
            },
            content = {

                Column(
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(it)
                    ) {
                        HomeView(this@MainActivity)
                    }

                }

            }
        )
    }

    @Composable
    fun AppBarView() {
        Surface(shadowElevation = 2.dp) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = this@MainActivity.getString(R.string.app_name).uppercase(),
                    style = body1Bold,
                    color = PrimaryColor,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )

                // hold feature
//                    Row() {
//                        IconButton(
//                            onClick = {
//
//                            }) {
//                            Icon(
//                                imageVector = Icons.Filled.Share,
//                                contentDescription = "Share",
//                                tint = Color.Black
//                            )
//                        }
//
//                        IconButton(onClick = {
//
//                        }) {
//                            Icon(
//                                imageVector = Icons.Filled.Settings,
//                                contentDescription = "Settings",
//                                tint = Color.Black
//                            )
//                        }
//                    }

            }

        }
    }

}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//fun MainActivityPreview() {
//    MainActivity().InitView()
//}