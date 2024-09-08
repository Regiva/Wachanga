package com.example.wachanga.util

import android.os.Build

fun isVersionLowerThanS(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.S

fun isVersionGreaterOrEqualThanO(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun isVersionGreaterOrEqualThanTiramisu(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
