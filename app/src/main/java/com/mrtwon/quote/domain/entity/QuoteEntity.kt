package com.mrtwon.quote.domain.entity

data class QuoteEntity(
    val quoteText: String,
    val quoteAuthor: String,
    val senderName: String,
    val senderLink: String,
    val quoteLink: String
){
    companion object{
        val empty = QuoteEntity(
            "", "", "", "", ""
        )
    }
}