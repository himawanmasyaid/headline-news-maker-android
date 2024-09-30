package com.hmwn.headlinenewsmaker.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.view.headline.CreateHeadlineActivity
import com.hmwn.headlinenewsmaker.view.main.MainActivity

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InitView()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

    @Composable
    fun InitView() {

        Surface(
            modifier =
            Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                val image = painterResource(R.drawable.ic_logo_hnm)
                Image(
                    painter = image,
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(100.dp),
                )
            }
        }

    }

}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//fun PreviewSplashScreen() {
//    SplashActivity().InitView()
//}
