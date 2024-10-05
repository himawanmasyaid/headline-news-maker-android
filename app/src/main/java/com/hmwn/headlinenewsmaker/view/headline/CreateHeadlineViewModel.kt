package com.hmwn.headlinenewsmaker.view.headline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.di.viewModelModule
import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource
import kotlinx.coroutines.launch

class CreateHeadlineViewModel(
    private val repository: HeadlineNewsDataSource
) : ViewModel() {

    private val _headlineEntity = MutableLiveData<HeadlineNewsEntity?>()
    val headlineEntityState: MutableLiveData<HeadlineNewsEntity?> get() = _headlineEntity

    fun insertHeadline(headline: HeadlineNewsEntity) {

        viewModelScope.launch {
            try {
                val isHeadlineAvailable = repository.isHeadlineAvailable(headline.headline)
                if (!isHeadlineAvailable) {
                    repository.insert(headline)
                }
            } catch (error : Exception) {

            }
        }

    }

    fun updateHeadline(headline: HeadlineNewsEntity) {
        viewModelScope.launch {
            try {
                repository.updateHeadlineNews(headline)
            } catch (error : Exception) {

            }
        }
    }

    fun getHeadlineData(id: Int) {
        viewModelScope.launch {
            try {
                if (id != 0 || id != null) {
                    val data = repository.getHeadlineNewsById(id)
                    _headlineEntity.value = data
                }
            } catch (error : Exception) {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _headlineEntity.value = null
    }

}