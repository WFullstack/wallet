package com.berakahnd.wallet.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berakahnd.wallet.data.local.Transaction
import com.berakahnd.wallet.ui.component.dashboard.AccountDialog
import com.berakahnd.wallet.ui.component.dashboard.LineChartScreen
import com.berakahnd.wallet.ui.component.dashboard.SaleCard
import com.berakahnd.wallet.ui.component.dashboard.TransactionDialog
import com.berakahnd.wallet.ui.viewmodel.HomeViewModel
import com.berakahnd.wallet.util.TRANSACTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel : HomeViewModel = hiltViewModel(),
    onNavigateBackClick : () -> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    var showTransDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uistate by viewModel.uistate
    val checkAmount by remember { mutableStateOf(true) }
    val totalAmount = formatNumber(uistate.totalAmount)
    val negativeAmount = formatNumber(uistate.totalNegativeAmount)
    val positiveAmount = formatNumber(uistate.totalPositiveAmount)

    val  data = listOf(Sale(
        title = "Total Amount",  value = totalAmount),
        Sale(title = "Total deposit", value = positiveAmount),
        Sale(title = "Total withdrawal",  value = negativeAmount))

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { onNavigateBackClick() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
                title = {
                    Text(text = "Dashboard")
                }, actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = null)
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier
                .padding(horizontal = 16.dp)) {
                Text(text = "Total Income")
                Text(text = totalAmount, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
            }

            ElevatedButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                enabled = when(uistate.totalAmount > 0f){
                    true -> checkAmount
                    false -> !checkAmount
                }
                ,onClick = {
                showTransDialog = true

            }) {
                Text(text = "Transaction")
            }

            if(uistate.data.isNotEmpty()){
                Text(modifier = Modifier
                    .padding(horizontal = 16.dp),text = "Chart Revenue", fontWeight = FontWeight.Bold)
                LineChartScreen(pointsData = uistate.pointsData)
            }
            Text(modifier = Modifier
                .padding(horizontal = 16.dp),text = "Sales Revenue", fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data){sale ->
                    SaleCard(sale)
                }
            }
        }
    }
    // dialog account manager
    if(showDialog){
        var amount = 0f
        AccountDialog(
            onDismissRequest = {
                showDialog = false
            }, onValueAmount  = { newAmount -> amount = newAmount.toFloat() },
            confirmButton = {
                val trans = Transaction(name = "Deposit" , amount = amount , type = TRANSACTION.DEPOSIT)
                viewModel.depositTransaction(trans)
                Toast.makeText(context,"The transaction is completed", Toast.LENGTH_LONG).show()
                showDialog = false
            }, dismissButton  = {showDialog = false}
        )
    }
    // dialog trans  manager
    if(showTransDialog){
        var name = "WithDrawal"
        var amount = 0f
        TransactionDialog(
            onDismissRequest = { showTransDialog = false},
            onValueAmount = { amountIt ->
                amount = amountIt.toFloat()
            },
            onValueName = { newName ->
                name = newName
            },
            onDismiss  = {
                showTransDialog = false
            },
            onPayment = {
                val trans = Transaction(name = name , amount = -(amount) , type = TRANSACTION.WITHDRAWAL)
                viewModel.depositTransaction(trans)
                Toast.makeText(context,"The transaction is completed", Toast.LENGTH_LONG).show()
                showTransDialog = false
            }
        )
    }
}

data class Sale(
    val title : String = "BUY",
    val value : String = "+0,25 BTC"
)

fun formatNumber(number: Float): String {
    return when {
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000)
        number >= 1_000 -> String.format("%.1fK", number / 1_000)
        else -> number.toString()
    }
}
