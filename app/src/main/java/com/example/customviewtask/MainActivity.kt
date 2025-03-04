package com.example.customviewtask

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shapesView = findViewById<MyCustomView>(R.id.shapesView)
        shapesView.setColors(listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW))
    }
}