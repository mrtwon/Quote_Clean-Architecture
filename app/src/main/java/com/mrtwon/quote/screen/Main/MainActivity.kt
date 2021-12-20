package com.mrtwon.quote.screen.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.mrtwon.quote.R
import com.mrtwon.quote.domain.entity.QuoteEntity
import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.manager.DependencyManager
import com.mrtwon.quote.manager.INetworkManager
import com.mrtwon.quote.manager.NetworkManager
import javax.inject.Inject


class MainActivity: AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    @Inject
    lateinit var networkManager: INetworkManager
    private val vm: MainViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java) }

    private lateinit var layoutQuoteBox: MaterialCardView
    private lateinit var layoutConnectError: LinearLayout
    private lateinit var layoutLoad: LinearLayout
    private lateinit var buttonNext: MaterialButton
    private lateinit var buttonShare: ImageButton
    private lateinit var tvQuoteText: TextView
    private lateinit var tvQuoteAuthor: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        listenerNetChange()
        layoutQuoteBox = findViewById(R.id.quote_box)
        layoutConnectError = findViewById(R.id.box_connect_error)
        layoutLoad = findViewById(R.id.layout_load)
        buttonNext = findViewById(R.id.btn_next)
        tvQuoteAuthor = findViewById(R.id.quote_author)
        tvQuoteText = findViewById(R.id.quote_text)
        buttonShare = findViewById(R.id.btn_share)
        vm.quoteEntityLiveData.observe(this, ::processData)
        vm.failureLiveData.observe(this, ::processFailure)
        vm.loadStateLiveData.observe(this, ::processLoad)
        buttonNext.setOnClickListener{ vm.getQuote() }
        buttonShare.setOnClickListener{ contentShare() }
        vm.getQuote()
    }

    private fun listenerNetChange(){
        networkManager.connectLiveData().observe(this){ networkState ->
            if(networkState.oldState == null) return@observe
            val msg = when(networkState.newState){
                true -> "Новый источник данных - Интернет"
                false -> "Новый источник данных - Локальное хранилище"
            }
            Snackbar.make(findViewById<FrameLayout>(R.id.placeSnackBar), msg, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun contentShare(){
        vm.quoteEntityLiveData.value?.let { mQuote ->
            val resultShare = "${mQuote.quoteText}\nАвтор ${mQuote.quoteAuthor}"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, resultShare)
            }
            startActivity(Intent.createChooser(intent, "Цитата"))
        }
    }

    private fun inject(){
        DependencyManager
            .plusMainScreenComponent()
            .inject(this)
    }

    private fun processData(quoteEntity: QuoteEntity){
        cleanState()
        tvQuoteText.text = quoteEntity.quoteText
        tvQuoteAuthor.text = if(quoteEntity.quoteAuthor.isEmpty()) "Неизвестен" else quoteEntity.quoteAuthor
        layoutQuoteBox.visibility = View.VISIBLE
    }

    private fun processFailure(failure: Failure){
        cleanState()
        layoutConnectError.visibility = View.VISIBLE
    }

    private fun processLoad(status: Boolean){
        cleanState()
        when(status){
            true -> layoutLoad.visibility = View.VISIBLE
            false -> layoutLoad.visibility = View.GONE
        }
    }

    private fun cleanState(){
        layoutQuoteBox.visibility = View.GONE
        layoutConnectError.visibility = View.GONE
        layoutLoad.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        vm.getQuote()
    }

    override fun onDestroy() {
        DependencyManager.cleanMainScreenComponent()
        super.onDestroy()
    }

    private fun log(s: String){
        Log.i("self-main", s)
    }

}