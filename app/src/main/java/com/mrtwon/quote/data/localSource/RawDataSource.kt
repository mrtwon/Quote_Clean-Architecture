package com.mrtwon.quote.data.localSource

import android.content.Context
import android.util.Log
import com.mrtwon.quote.R
import com.mrtwon.quote.data.models.QuoteModel
import com.mrtwon.quote.domain.entity.QuoteEntity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class RawDataSource @Inject constructor(val context: Context) {
    private val RES_RAW_ID: Int = R.raw.quotes

    private fun getAllListQuotes(): List<QuoteModel>{
        val quoteList = arrayListOf<QuoteModel>()
        val rawText = readText()
        val arrayQuotes = JSONObject(rawText).getJSONArray("quotes")
        for(index in 0 until arrayQuotes.length()){
            val currentJsonObject = arrayQuotes.getJSONObject(index)
            quoteList.add(
                QuoteModel(
                quoteText = currentJsonObject.getString("quoteText"),
                quoteLink = currentJsonObject.getString("quoteLink"),
                quoteAuthor = currentJsonObject.getString("quoteAuthor"),
                senderName = currentJsonObject.getString("senderName"),
                senderLink = currentJsonObject.getString("senderLink")
            )
            )
        }
        return quoteList
    }

    fun randomQuote(): QuoteModel{
        val listQuotes = getAllListQuotes()
        return listQuotes[randomRange(max = listQuotes.size-1)]
    }

    private fun randomRange(min: Int = 0, max: Int): Int{
        return ((Math.random() * (max-min)+1)+min).toInt()
    }

    private fun readText(): String{
        val stringBuilder = StringBuilder()
        val inputStream = context.resources.openRawResource(RES_RAW_ID)
        BufferedReader(InputStreamReader(inputStream))
            .readLines()
            .map { str -> "$str\n" }
            .forEach { str -> stringBuilder.append(str) }
        return stringBuilder.toString()
    }
}