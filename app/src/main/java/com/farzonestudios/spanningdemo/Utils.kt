package com.farzonestudios.spanningdemo

import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.getColorCompat(@ColorRes id: Int) =
    ContextCompat.getColor(getApplication(), id)

fun AndroidViewModel.getFont(@FontRes id: Int) = ResourcesCompat.getFont(getApplication(), id)