package com.hmwn.headlinenewsmaker.view

import android.annotation.SuppressLint
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
import com.hmwn.headlinenewsmaker.view.home.HomeView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.ui.theme.black5
import com.hmwn.headlinenewsmaker.ui.theme.body1
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.ui.theme.h7
import com.hmwn.headlinenewsmaker.ui.theme.h7Medium

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
                AppBarView("Headline News")
            },
            content = {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(it)
                ) {
                    HomeView(this@MainActivity)
                }
            }
        )
    }

    @Composable
    fun AppBarView(
        title: String
    ) {
        Surface(shadowElevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        text = title,
                        style = body1Medium,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .weight(1f)
                    )

                    // lock feature
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

//                Spacer(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .fillMaxWidth()
//                        .background(black5)
//                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity().InitView()
}