package com.hmwn.headlinenewsmaker.data.model

class DataHolder private constructor() {
    companion object {
        private var _byteArray: ByteArray? = null

        fun setByteArray(byteArray: ByteArray) {
            _byteArray = byteArray
        }

        fun getByteArray(): ByteArray? {
            return _byteArray
        }

        fun clear() {
            _byteArray = null
        }
    }
}