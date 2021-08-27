package com.example.streetmusic2.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.streetmusic2.ui.navigation.MainNavGraph
import com.example.streetmusic2.ui.theme.StreetMusic2Theme
import com.example.streetmusic2.util.userpref.LocalUserPref
import com.example.streetmusic2.util.userpref.UserSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    @Inject
    lateinit var userPrefs: UserSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CompositionLocalProvider(LocalUserPref provides userPrefs) {
                StreetMusic2Theme {
                    Surface(color = MaterialTheme.colors.background) {
                        MainNavGraph(navController)
                    }
                }
            }
        }
    }
}