package com.hmwn.headlinenewsmaker.view.createnews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmwn.headlinenewsmaker.view.preview.PreviewNewsActivity

class CreateNewsActivity : ComponentActivity() {

    val headline = mutableStateOf("")
    val author = mutableStateOf("")
    val datetime = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreateNewsView()
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateNewsView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = headline.value,
                onValueChange = { headline.value = it },
                label = { Text("Headline") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = author.value,
                onValueChange = { author.value = it },
                label = { Text("Author") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = datetime.value,
                onValueChange = { datetime.value = it },
                label = { Text("Datetime") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {

                    setLog("Create News")
                    setLog("headline : ${headline.value}")
                    setLog("author : ${author.value}")
                    setLog("datetime : ${datetime.value}")

//                val inputData = InputData(
//                    viewModel.headline.value,
//                    viewModel.author.value,
//                    viewModel.datetime.value
//                )
//                onNavigate(inputData)

                    startActivity(Intent(this@CreateNewsActivity, PreviewNewsActivity::class.java))

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Next")
            }
        }
    }

    private fun setLog(msg: String) {
        Log.e("create", msg)
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)@Composable
fun PreviewSplashScreen() {
    CreateNewsActivity().CreateNewsView()
}
