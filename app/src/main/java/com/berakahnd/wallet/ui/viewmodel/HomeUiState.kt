package com.berakahnd.wallet.ui.viewmodel

import co.yml.charts.common.model.Point
import com.berakahnd.wallet.data.local.Transaction

data class HomeUiState (
    val pointsData :  List<Point> = listOf(Point(0f, 0f)),
    val totalPositiveAmount :  Float = 0f,
    val totalNegativeAmount : Float = 0f,
    val totalAmount : Float = 0f,
    val data : List<Transaction> = emptyList(),
    val isLoading : Boolean = false,
    val error : String = ""
)