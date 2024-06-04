package com.hmwn.headlinenewsmaker.view.createnews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import kotlinx.coroutines.launch

class CreateNewsViewModel(
    private val headlineNewsRepository: HeadlineNewsRepository
) : ViewModel() {

    private val _headlinesCreate = MutableLiveData<Long>()
    val headlinesCreateState: LiveData<Long> = _headlinesCreate

    fun insertHeadline(headline: HeadlineNewsEntity) {
        viewModelScope.launch {

            setLog("insert headline : $headline")
            val response = headlineNewsRepository.insert(headline)
            _headlinesCreate.value = response
        }
    }

    private fun setLog(msg: String) {
        Log.e("create", msg)
    }

}