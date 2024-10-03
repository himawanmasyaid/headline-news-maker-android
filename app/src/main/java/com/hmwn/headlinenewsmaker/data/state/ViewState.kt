package com.hmwn.headlinenewsmaker.data.state

sealed class ViewState<T> {
    data class Loading<T>(var progress: Float? = null) : ViewState<T>()
    data class Success<T>(var data: T) : ViewState<T>()
    data class Error<T>(var message: String? = null) : ViewState<T>()
    data class Idle<T>(val unit: Unit?) : ViewState<T>()
}