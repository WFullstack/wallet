package com.berakahnd.wallet.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berakahnd.wallet.ui.theme.WalletTheme

@Composable
fun StartedScreen(
    onStartedClick : () -> Unit
){
    Scaffold { paddingValues ->
        Surface(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 3000))
                ) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        imageVector = Icons.Default.Wallet,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                Modifier.padding(vertical = 16.dp),
                //Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                Text(text = "The World Comes for Financial Advice", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.size(24.dp))
                OutlinedButton(modifier = Modifier, onClick = { onStartedClick() }) {
                    Text(text = "Started")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StartedScreenPreview() {
    WalletTheme {
        /*val transaction = Transaction(ref = "xxxxx", amount = 3000f)
        ItemTransaction(transaction){}*/
        //MainScreen()
        //ActivityTrabsaction()
        StartedScreen(){

        }
    }
}