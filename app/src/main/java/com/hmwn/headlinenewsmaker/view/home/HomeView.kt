package com.hmwn.headlinenewsmaker.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.ui.theme.PrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.Purple80
import com.hmwn.headlinenewsmaker.ui.theme.SoftPrimaryColor
import com.hmwn.headlinenewsmaker.ui.theme.black2
import com.hmwn.headlinenewsmaker.ui.theme.black3
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
import com.hmwn.headlinenewsmaker.ui.theme.poppinsMedium
import com.hmwn.headlinenewsmaker.view.MainActivity
import com.hmwn.headlinenewsmaker.view.card.HeadlineNewsCard
import com.hmwn.headlinenewsmaker.view.createnews.CreateNewsActivity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(context: Context) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(SoftPrimaryColor)
                    .fillMaxWidth()
                    .padding(top = 21.dp, bottom = 38.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.headline),
                    style = h4
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = stringResource(R.string.headline_desc),
                    style = body3
                )

            }


            Button(
                onClick = {
                    context.startActivity(Intent(context, CreateNewsActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = PrimaryColor
                ),
                modifier = Modifier
                    .padding(
                        start = 16.dp, end = 16.dp,
                    )
                    .background(
                        PrimaryColor,
                        shape = RoundedCornerShape(30.dp)
                    ) // Set rounded corners
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            ) {
                Text(
                    text = stringResource(R.string.start_create_headline),
                    textAlign = TextAlign.Center,
                    style = body2Bold,
                    color = Color.White
                ) // Center text
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.headline_history),
            style = body1Medium,
        )

        Spacer(modifier = Modifier.height(18.dp))

        val news: List<News> = listOf<News>(
            News(),
            News(),
            News(),
        )

        HeadlineHistoryList(context, emptyList())

    }

}


@Composable
fun HeadlineHistoryList(context: Context, news: List<News>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter
    ) {

        if (news.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.empty_state_title),
                    color = black2,
                    style = body1Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.empty_state_desc),
                    color = black2,
                    style = body2
                )

            }

        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(news) {
                    HeadlineNewsCard(it)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MainActivityPreview() {
//    HomeView(LocalContext.current)
//}