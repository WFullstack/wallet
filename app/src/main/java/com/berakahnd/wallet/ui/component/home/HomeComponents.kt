package com.berakahnd.wallet.ui.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berakahnd.wallet.data.local.Transaction
import com.berakahnd.wallet.ui.screens.formatNumber
import com.berakahnd.wallet.ui.theme.Green
import com.berakahnd.wallet.ui.theme.Pink80
import com.berakahnd.wallet.ui.theme.Purple
import com.berakahnd.wallet.ui.viewmodel.HomeViewModel
import com.berakahnd.wallet.util.TRANSACTION
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTransaction(transaction: Transaction, onDeleteClick : (Transaction) -> Unit){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = dateFormat.format(transaction.date)
    val type = when(transaction.type){
        TRANSACTION.DEPOSIT -> "Deposit"
        TRANSACTION.WITHDRAWAL -> "Withdrawal"
    }
    val amount = formatNumber(transaction.amount)
    Card(onClick = { /*TODO*/ },
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
                contentAlignment = Alignment.Center){
                Icon(imageVector = Icons.Default.SwapHoriz, contentDescription = null,
                    tint = if(transaction.type == TRANSACTION.DEPOSIT) Color.Green else Color.Red
                )
            }
            Column(modifier = Modifier.fillMaxWidth(0.5f),verticalArrangement = Arrangement.spacedBy(5.dp), horizontalAlignment = Alignment.Start) {
                Text(text = transaction.name, color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
                Text(text = type, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Start)
            }

            Column(modifier = Modifier.fillMaxWidth(0.5f),horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {

                Text(textAlign = TextAlign.End, text = amount,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(modifier = Modifier.size(40.dp),onClick = { onDeleteClick(transaction) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTrabsaction(
    viewModel : HomeViewModel = hiltViewModel(),
    onShowDashboardClick : () -> Unit
){
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp * 35 / 100
    val screenWidth = configuration.screenWidthDp.dp

    val width = screenWidth / 2
    val height = screenHeight / 2
    val uistate by viewModel.uistate
    val negativeAmount = formatNumber(uistate.totalNegativeAmount)
    val positiveAmount = formatNumber(uistate.totalPositiveAmount)
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .size(width = screenWidth, height = screenHeight),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Card(
            modifier = Modifier
                .size(width = width, height = screenHeight), shape = RoundedCornerShape(16.dp),
            onClick = { onShowDashboardClick() }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(modifier = Modifier.size(100.dp),imageVector = Icons.Default.BarChart,
                    contentDescription = null, tint = Green
                )
                Text(text = "Activity", style = MaterialTheme.typography.titleMedium)
                Text(text = "of current week", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            modifier = Modifier.size(width = width, height = screenHeight),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .size(width = width, height = height),
                shape = RoundedCornerShape(16.dp),

                onClick = { /*TODO*/ }
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center,
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = Pink80)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Withdrawal")
                    Text(text = negativeAmount, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Card(
                modifier = Modifier
                    .size(width = width, height = height),

                shape = RoundedCornerShape(16.dp),
                onClick = { /*TODO*/ }
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.PieChart, contentDescription = null, tint = Purple)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Deposit")
                    Text(text = positiveAmount, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
