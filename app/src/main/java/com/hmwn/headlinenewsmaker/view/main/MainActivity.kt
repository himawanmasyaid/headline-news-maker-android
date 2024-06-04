package com.hmwn.headlinenewsmaker.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.ui.theme.PrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.view.main.home.HomeView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startObserveData()
        viewModel.getHeadlineNews()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                InitView()
            }
        }

    }

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
                        HomeView(this@MainActivity, viewModel)
                    }

                }

            }
        )
    }

    private fun startObserveData() {

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
                    text = stringResource(R.string.app_name),
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

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun MainActivityPreview() {
    MainActivity().InitView()
}