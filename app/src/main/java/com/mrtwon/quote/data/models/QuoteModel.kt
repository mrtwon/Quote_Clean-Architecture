package com.mrtwon.quote.data.models

import com.google.gson.annotations.SerializedName
import com.mrtwon.quote.domain.entity.QuoteEntity

data class QuoteModel(

	@field:SerializedName("quoteLink")
	val quoteLink: String,

	@field:SerializedName("quoteText")
	val quoteText: String,

	@field:SerializedName("senderName")
	val senderName: String,

	@field:SerializedName("quoteAuthor")
	val quoteAuthor: String,

	@field:SerializedName("senderLink")
	val senderLink: String
) {
	fun toQuoteEntity(): QuoteEntity{
		return QuoteEntity(quoteText, quoteAuthor, senderName, senderLink, quoteLink)
	}
	companion object{
		val empty = QuoteModel(
			"", "", "", "", ""
		)
	}
}
