package com.hmwn.headlinenewsmaker.view.splash

import android.content.Intent
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hmwn.headlinenewsmaker.ui.theme.HeadlineNewsMakerTheme
import com.hmwn.headlinenewsmaker.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashView()
        }

        runBlocking {
            delay(5000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
}

@Composable
private fun SplashView() {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("NEWS")

//            Image(
//                painter = painterResource(R.drawable.ic_launcher),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .align(Alignment.Center),
//            )
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)@Composable
fun PreviewSplashScreen() {
    SplashView()
}
