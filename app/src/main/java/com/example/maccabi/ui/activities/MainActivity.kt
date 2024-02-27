package com.example.maccabi.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.maccabi.navigation.MaccabiNavigation
import com.example.maccabi.ui.theme.MaccabiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaccabiTheme {
                MaccabiNavigation()
            }
        }
    }
}
