package com.berakahnd.wallet.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.berakahnd.wallet.ui.screens.AppNavHost
import com.berakahnd.wallet.ui.theme.WalletTheme
import com.berakahnd.wallet.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel  = ViewModelProvider(this)[HomeViewModel::class]
        setContent {
            WalletTheme {
                val navController = rememberNavController()
                AppNavHost(
                    viewModel = viewModel,
                    navController = navController,
                )
            }
        }
    }
}
