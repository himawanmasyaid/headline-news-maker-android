package com.hmwn.headlinenewsmaker.view.createnews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.R
import com.hmwn.headlinenewsmaker.common.getCurrentDateTime
import com.hmwn.headlinenewsmaker.common.toast
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import com.hmwn.headlinenewsmaker.ui.theme.FontPrimary
import com.hmwn.headlinenewsmaker.ui.theme.black1
import com.hmwn.headlinenewsmaker.ui.theme.body3
import com.hmwn.headlinenewsmaker.ui.theme.hint
import com.hmwn.headlinenewsmaker.view.components.ToolbarView
import com.hmwn.headlinenewsmaker.view.main.MainViewModel
import com.hmwn.headlinenewsmaker.view.preview.PreviewNewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class CreateNewsActivity : ComponentActivity() {

    private val viewModel by viewModel<CreateNewsViewModel>()

    val headlineState = mutableStateOf("")
    val authorState = mutableStateOf("")
    val datetimeState = mutableStateOf(getCurrentDateTime())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startObserveData()

        setContent {
            InitView()
        }

    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun InitView() {

        Scaffold(
            topBar = {
                ToolbarView("Create Headline") {
                    finish()
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {

                        val headline = headlineState.value
                        val author = authorState.value
                        val datetime = datetimeState.value

                        if (headline.isEmpty() || author.isEmpty() || datetime.isEmpty()) {
                            toast("please input data")
                        } else {

                            viewModel.insertHeadline(
                                HeadlineNewsEntity(
                                    headline = headline,
                                    author = author,
                                    datetime = datetime,
                                    image = "",
                                )
                            )
                        }

                    }
                ) {
                    Text(
                        stringResource(R.string.preview_now),
                        fontSize = 16.sp,
                        fontFamily = FontPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
                    )
                }
            },
            content = {

                Box(
                    Modifier
                        .background(Color.White)
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            stringResource(R.string.headline),
                            style = body3,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )

                        OutlinedTextField(
                            value = headlineState.value,
                            onValueChange = { headlineState.value = it },
                            placeholder = {
                                Text(
                                    stringResource(R.string.input_headline),
                                    color = hint
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )

                        Text(
                            stringResource(R.string.author),
                            style = body3,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 16.dp)
                        )

                        OutlinedTextField(
                            value = authorState.value,
                            onValueChange = { authorState.value = it },
                            placeholder = {
                                Text(
                                    stringResource(R.string.input_author),
                                    color = hint
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Text(
                            stringResource(R.string.datetime),
                            style = body3,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 16.dp)
                        )

                        OutlinedTextField(
                            value = datetimeState.value,
                            onValueChange = { datetimeState.value = it },
                            placeholder = {
                                Text(
                                    stringResource(R.string.input_datetime),
                                    color = hint
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Send
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        )

    }

    private fun startObserveData() {


        viewModel.headlinesCreateState.observe(this) {

            val headline = headlineState.value
            val author = authorState.value
            val datetime = datetimeState.value

            val intent =
                Intent(this@CreateNewsActivity, PreviewNewsActivity::class.java)
            intent.putExtra(PreviewNewsActivity.HEADLINE_ARG, headline)
            intent.putExtra(PreviewNewsActivity.AUTHOR_ARG, author)
            intent.putExtra(PreviewNewsActivity.DATETIME_ARG, datetime)
            startActivity(intent)
        }

    }

    private fun setLog(msg: String) {
        Log.e("create", msg)
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewSplashScreen() {
    CreateNewsActivity().InitView()
}
