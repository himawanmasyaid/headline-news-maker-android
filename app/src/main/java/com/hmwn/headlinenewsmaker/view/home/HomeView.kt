package com.hmwn.headlinenewsmaker.view.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.view.MainActivity
import com.hmwn.headlinenewsmaker.view.createnews.CreateNewsActivity

@Composable
fun HomeView(context: Context? = null) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(onClick = {
                context?.startActivity(Intent(context, CreateNewsActivity::class.java))
            }, icon = R.drawable.ic_baseline_add_white, text = "Create Headline News")

            Spacer(modifier = Modifier.height(8.dp))

            NewsCard(
                News(
                    "Minta BUatkan Desain rumah, Ibu Ini Beri Imbalan Dijodohkan dengan Anaknya",
                    "Himawan",
                    "Kamis, 25 May 2024"
                )
            )
            NewsCard(
                News(
                    "Baru 3 Hari Menikah, Istri Kabur karena Tahu Gaji Suami Hanya Rp 3,9 Juta",
                    "Raditya",
                    "This is another sample message"
                )
            )
        }
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, icon: Int, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF60E9FF))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = text, color = Color.White)
        }
    }
}

@Composable
fun NewsCard(news: News) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_baseline_add_white),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = news.headline,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = news.author)
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun PreviewHomeView() {
    HomeView()
}