package com.berakahnd.wallet.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.berakahnd.wallet.data.local.Transaction
import com.berakahnd.wallet.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WalletRepository
) : ViewModel(){
    val uistate = mutableStateOf(HomeUiState())

    init {
        getAllTransaction()
    }

    private fun getAllTransaction(){
        uistate.value = uistate.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                repository.getAllTransaction().collect{ result ->
                    uistate.value = uistate.value.copy(
                        data = result, isLoading = false,
                        totalAmount = repository.getTotalAmount(),
                        totalNegativeAmount = repository.getTotalNegativeAmount(),
                        totalPositiveAmount = repository.getTotalPositiveAmount()
                    )
                    getDataPoint(result)
                }
            }catch ( e : Exception){
                uistate.value = uistate.value.copy(error = e.message.toString())
            }
        }
    }
    fun depositTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.depositTransaction(transaction)
        }
    }
    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.deleteTransaction(transaction)

        }
        getTotalAmount()
    }
    fun getTotalAmount() {
        viewModelScope.launch {
            uistate.value = uistate.value.copy(
                totalAmount = repository.getTotalAmount()
            )
        }
    }
    fun getTotalNegativeAmount() {
        viewModelScope.launch {
            uistate.value = uistate.value.copy(
                totalNegativeAmount = repository.getTotalNegativeAmount()
            )
        }
    }
    fun getTotalPositiveAmount() {
        viewModelScope.launch {
            uistate.value = uistate.value.copy(
                totalPositiveAmount = repository.getTotalPositiveAmount()
            )
        }
    }
    fun getDataPoint(result: List<Transaction>) {
        try {
            val expensePoints: List<Point> = result.reversed().mapIndexed { index, transaction ->
                var amount = transaction.amount
                //amount = uistate.value.totalAmount + amount

                Point(index.toFloat(), amount)
            }
            uistate.value = uistate.value.copy(
                pointsData = expensePoints
            )
        } catch (e: Exception) {
            print(e.message)
        }
    }
}