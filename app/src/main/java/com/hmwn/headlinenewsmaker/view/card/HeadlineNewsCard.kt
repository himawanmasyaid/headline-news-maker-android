package com.hmwn.headlinenewsmaker.view.card

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.data.model.News
import com.hmwn.headlinenewsmaker.ui.theme.black1
import com.hmwn.headlinenewsmaker.ui.theme.black2
import com.hmwn.headlinenewsmaker.ui.theme.black3
import com.hmwn.headlinenewsmaker.ui.theme.black4
import com.hmwn.headlinenewsmaker.ui.theme.black5
import com.hmwn.headlinenewsmaker.ui.theme.body1
import com.hmwn.headlinenewsmaker.ui.theme.body1Bold
import com.hmwn.headlinenewsmaker.ui.theme.body1Medium
import com.hmwn.headlinenewsmaker.ui.theme.body2
import com.hmwn.headlinenewsmaker.ui.theme.body2Bold
import com.hmwn.headlinenewsmaker.ui.theme.body2Medium
import com.hmwn.headlinenewsmaker.ui.theme.body3
import com.hmwn.headlinenewsmaker.ui.theme.body3Bold
import com.hmwn.headlinenewsmaker.ui.theme.body4
import com.hmwn.headlinenewsmaker.ui.theme.h1
import com.hmwn.headlinenewsmaker.ui.theme.h7
import com.hmwn.headlinenewsmaker.view.preview.PreviewNewsActivity
import java.io.File
import java.io.FileOutputStream

@Composable
fun HeadlineNewsCard(context: Context, news: HeadlineNewsEntity) {

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .clickable {
                val intent = Intent(context, PreviewNewsActivity::class.java)
                intent.putExtra(PreviewNewsActivity.HEADLINE_ARG, news.headline)
                intent.putExtra(PreviewNewsActivity.AUTHOR_ARG, news.author)
                intent.putExtra(PreviewNewsActivity.DATETIME_ARG, news.datetime)
                context.startActivity(intent)
            }
        ,

    ) {

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = news.headline,
            style = body1Bold,
            color = black1
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = news.author,
                style = body2Medium,
                color = black2
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "-",
                style = body2Medium,
                color = black2
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = news.datetime,
                style = body2Medium,
                color = black2
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(black5)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // show image
//        Image(
//            bitmap = loadBitmapFromStorage(article.image),
//            contentDescription = null
//        )

    }


    fun saveImageToStorage(image: Bitmap, context: Context): String {
        val directory = context.getExternalFilesDir(null)
        val file = File(directory, "image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return file.path
    }

}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//private fun HeadlineNewsCardView() {
//    HeadlineNewsCard(
//        News(
//            "Baru 3 Hari Menikah, Istri Kabur karena Tahu Gaji Suami Hanya Rp 3,9 Juta",
//            "Umar Syaid Himawan",
//            "Kamis, 25 Mei 2024 - 20:00"
//        )
//    )
//}