package com.hmwn.headlinenewsmaker.view.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource

class MainViewModel(
    private val headlineNewsRepository: HeadlineNewsDataSource
) : ViewModel() {

    private val _headlines = MutableLiveData<List<HeadlineNewsEntity>>()
    val headlinesState: LiveData<List<HeadlineNewsEntity>> = _headlines

    fun getHeadlineNews() {
        viewModelScope.launch {
            val response = headlineNewsRepository.getAllHeadlineNews()
            _headlines.value = response
        }
    }

}