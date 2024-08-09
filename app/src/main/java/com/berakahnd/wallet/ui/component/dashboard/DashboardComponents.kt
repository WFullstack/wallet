package com.berakahnd.wallet.ui.component.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.berakahnd.wallet.ui.screens.Sale
import com.berakahnd.wallet.ui.theme.Pink80
import com.berakahnd.wallet.util.Tools.formatNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleCard(sale: Sale){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp * 35 / 100
    val screenWidth = configuration.screenWidthDp.dp
    val width = screenWidth / 2
    val height = screenHeight / 2

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
            Icon(modifier = Modifier.size(30.dp), imageVector = Icons.Default.ShowChart, contentDescription = null, tint = Pink80)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = sale.title,fontWeight = FontWeight.Bold)
            Text(text = sale.value, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun AccountDialog(
    onDismissRequest : () -> Unit,
    onValueAmount : (String) -> Unit,
    confirmButton : () -> Unit,
    dismissButton : () -> Unit
) {
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text("Balance") },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        amount = newAmount
                        try{
                            onValueAmount(newAmount)
                        }catch (e : Exception){
                            Log.i("niamien",e.toString())}
                    },
                    label = { Text("Add a amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {confirmButton() }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissButton() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TransactionDialog(
    onDismissRequest : () -> Unit,
    onValueName : (String) -> Unit,
    onValueAmount : (String) -> Unit,
    onDismiss : () -> Unit,
    onPayment : () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text("Transaction") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { newName ->
                        name = newName
                        try{
                            onValueName(name)
                        }catch (e : Exception){
                            Log.i("niamien",e.toString())}
                    },
                    placeholder = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(8.dp))
                TextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        amount = newAmount
                        try{
                            onValueAmount(amount)
                        }catch (e : Exception){
                            Log.i("niamien",e.toString())}
                    },
                    placeholder = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {onPayment() }) {
                Text("Payment")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun LineChartScreen(
    pointsData: List<Point>
) {
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .axisLabelColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .axisLineColor(MaterialTheme.colorScheme.onSurface)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val maxY = pointsData.maxOfOrNull { it.y } ?: 0f
    val minY = pointsData.minOfOrNull { it.y } ?: 0f
    val yAxisSteps = 1

    val yAxisData = AxisData.Builder()
        .steps(yAxisSteps)
        .backgroundColor(MaterialTheme.colorScheme.primaryContainer)
        .axisLabelColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .axisLineColor(MaterialTheme.colorScheme.onSurface)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val range = maxY - minY
            val stepValue = minY + (i * range / yAxisSteps)
            val strstepValue = formatNumber(stepValue)
            //stepValue.formatToSinglePrecision()
            strstepValue
        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = MaterialTheme.colorScheme.surface
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), lineChartData = lineChartData
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    //WalletTheme {}
    val pointsData: List<Point> =
        listOf(
            Point(0f, 40f), Point(1f, 90f), Point(2f, 0f),
            Point(3f, 60f), Point(4f, 10f)
        )

    LineChartScreen(pointsData)
}