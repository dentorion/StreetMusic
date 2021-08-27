package com.example.streetmusic2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.example.streetmusic2.ui.theme.StreetMusic2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreetMusic2Theme {
                Surface(color = MaterialTheme.colors.background) {
                    Text("Hello, world!")
                }
            }
        }
    }
}