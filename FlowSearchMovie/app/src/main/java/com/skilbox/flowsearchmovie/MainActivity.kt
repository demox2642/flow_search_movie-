package com.skilbox.flowsearchmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skilbox.flowsearchmovie.database.Database

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Database.init(context = this)
    }
}
