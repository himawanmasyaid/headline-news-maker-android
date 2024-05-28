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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.ui.theme.body1
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.ui.theme.body2Bold
import com.hmwn.headlinenewsmaker.ui.theme.h7
import com.hmwn.headlinenewsmaker.ui.theme.h7Medium
import com.hmwn.headlinenewsmaker.ui.theme.poppinsMedium
import com.hmwn.headlinenewsmaker.view.MainActivity
import com.hmwn.headlinenewsmaker.view.card.HeadlineNewsCard
import com.hmwn.headlinenewsmaker.view.createnews.CreateNewsActivity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(context: Context? = null) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(onClick = {
            context?.startActivity(Intent(context, CreateNewsActivity::class.java))
        }, icon = R.drawable.ic_baseline_add_white, text = "Create Headline News")

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Headline History",
            style = h7Medium,
        )

        Spacer(modifier = Modifier.height(18.dp))

        val news: List<News> = listOf<News>(
            News(),
            News(),
            News(),
        )

        Box() {
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

@Composable
fun CustomButton(onClick: () -> Unit, icon: Int, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF13ade9))
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

            Text(
                text = text,
                color = Color.White,
                style = body1Bold,
                letterSpacing = 0.5.sp
            )

        }
    }
}


//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//private fun PreviewHomeView() {
//    HomeView()
//}