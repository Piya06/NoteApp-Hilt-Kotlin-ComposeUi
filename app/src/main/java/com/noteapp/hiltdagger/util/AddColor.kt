package com.noteapp.hiltdagger.util

import androidx.annotation.ArrayRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberColorArray(@ArrayRes arrayResId: Int): List<Color>{

    val context = LocalContext.current
    return remember(arrayResId){
        val typedArray = context.resources.obtainTypedArray(arrayResId)
        val colors = mutableListOf<Color>()
        try {
            for (i in 0 until typedArray.length()){
                val colorInt = typedArray.getColor(i,0)
                colors.add(Color(colorInt))
            }
        } finally {
            typedArray.recycle()
        }
        colors
    }

}