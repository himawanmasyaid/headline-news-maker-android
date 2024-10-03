package com.hmwn.headlinenewsmaker.view.headline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.repo.HeadlineNewsRepository
import com.hmwn.headlinenewsmaker.repo.datasource.HeadlineNewsDataSource
import kotlinx.coroutines.launch

class CreateHeadlineViewModel(
    private val repository: HeadlineNewsDataSource
) : ViewModel() {

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

}