package com.berakahnd.wallet.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berakahnd.wallet.ui.component.home.ActivityTrabsaction
import com.berakahnd.wallet.ui.component.home.ItemTransaction
import com.berakahnd.wallet.ui.theme.WalletTheme
import com.berakahnd.wallet.ui.viewmodel.HomeViewModel
import com.berakahnd.wallet.util.Tools.formatNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onShowDashboardClick : () -> Unit,
    viewModel : HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var seeAll by rememberSaveable { mutableStateOf(false) }
    val uistate by viewModel.uistate
    val totalAmount = formatNumber(uistate.totalAmount)
    val data = when(seeAll){
        true -> {
            uistate.data
        }
        false -> {
            uistate.data.take(4)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                              Text(text = "Wallet")
            }, actions = {
                IconButton(onClick = { onShowDashboardClick() }) {
                    Icon(imageVector = Icons.Default.Wallet, contentDescription = null)
                }
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier
                .padding(horizontal = 16.dp)) {
                Text(text = "welcome back")
                Text(text = totalAmount, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
            }
            ActivityTrabsaction(viewModel = viewModel, onShowDashboardClick = { onShowDashboardClick() })
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Transactions", fontWeight = FontWeight.Bold)
                TextButton(onClick = { seeAll = !seeAll }) {
                    Text(text = if(seeAll) "Hide" else "See All")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(data){transaction ->
                    ItemTransaction(transaction, onDeleteClick =  { newTransaction ->
                        viewModel.deleteTransaction(newTransaction)
                        Toast.makeText(context,"The transaction is deleted", Toast.LENGTH_LONG).show()
                    }
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WalletTheme {
        //val transaction = Transaction(ref = "xxxxx", amount = 3000f)
        //ItemTransaction(transaction){}
        //MainScreen()
        //ActivityTrabsaction()
    }
}