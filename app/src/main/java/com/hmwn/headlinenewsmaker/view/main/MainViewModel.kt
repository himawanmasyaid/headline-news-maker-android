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

class MainViewModel(
    private val headlineNewsRepository: HeadlineNewsRepository
) : ViewModel() {

    private val _headlines = MutableLiveData<List<HeadlineNewsEntity>>()
    val headlinesState: LiveData<List<HeadlineNewsEntity>> = _headlines

    // state
    private val _newsState: MutableState<List<HeadlineNewsEntity>> = mutableStateOf(emptyList())
    val newsState: State<List<HeadlineNewsEntity>> = _newsState

    fun getHeadlineNews() {
        viewModelScope.launch {
            val response = headlineNewsRepository.getAll()
            _headlines.value = response
            _newsState.value = response
        }
    }

}