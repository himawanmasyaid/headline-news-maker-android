package com.hmwn.headlinenewsmaker.view.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.ui.theme.black3
import com.hmwn.headlinenewsmaker.ui.theme.black4
import com.hmwn.headlinenewsmaker.ui.theme.body1
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.ui.theme.body2
import com.hmwn.headlinenewsmaker.ui.theme.body3
import com.hmwn.headlinenewsmaker.ui.theme.h1
import com.hmwn.headlinenewsmaker.ui.theme.h7

@Composable
fun HeadlineNewsCard(news: News) {

    Column(
    ) {
        Text(
            text = news.headline,
            style = body1Bold,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = news.author,
            style = body1Medium,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = news.datetime,
            style = body2,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(black4)
        )
        Spacer(modifier = Modifier.height(6.dp))

    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HeadlineNewsCardView() {
    HeadlineNewsCard(
        News(
            "Baru 3 Hari Menikah, Istri Kabur karena Tahu Gaji Suami Hanya Rp 3,9 Juta",
            "Umar Syaid Himawan",
            "Kamis, 25 Mei 2024 - 20:00"
        )
    )
}