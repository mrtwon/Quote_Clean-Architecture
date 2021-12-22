package com.mrtwon.quote.data.networkDataSource

data class NetworkResponse <T> (val isSuccessful: Boolean, val response: T?)