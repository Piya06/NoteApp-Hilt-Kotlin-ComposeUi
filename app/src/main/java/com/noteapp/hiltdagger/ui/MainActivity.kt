package com.noteapp.hiltdagger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.noteapp.hiltdagger.ui.theme.NoteAppHiltDaggerTheme
import com.noteapp.hiltdagger.util.AnalyticsTracker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsTracker: AnalyticsTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticsTracker.trackScreenView("MainActivity")

        enableEdgeToEdge()
        setContent {
            NoteAppHiltDaggerTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                    NoteScreen()
                }
            }
        }
    }
}