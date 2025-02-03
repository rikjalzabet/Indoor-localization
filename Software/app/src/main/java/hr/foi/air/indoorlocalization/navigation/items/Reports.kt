package hr.foi.air.indoorlocalization.navigation.items


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.core.parser.assetPositionHistoryList
import hr.foi.air.core.parser.assetZoneHistoryList
import hr.foi.air.core.parser.liveAssetPositionList
import hr.foi.air.indoorlocalization.navigation.items.ReportsItems.AssetPositionHistoryList
import hr.foi.air.indoorlocalization.navigation.items.ReportsItems.AssetZoneHistoryList
import hr.foi.air.indoorlocalization.parser.JsonDataParser
import hr.foi.air.save_object_info.IReportGenerator
import hr.foi.air.save_object_info.JSONReportGenerator
import hr.foi.air.save_object_info.PDFReportGenerator
import hr.foi.air.ws.TestData.assetPositionHistoryJSON
import hr.foi.air.ws.TestData.assetZoneHistoryJSON
import hr.foi.air.ws.TestData.testAssetPositionJSON
import java.io.File

@Composable
fun ToggleButtonReports(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = text
        )
    }
}

@Composable
fun Reports() {
    val context = LocalContext.current
    val radioOptions = listOf("Asset History", "Zone History")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                radioOptions.forEach { text ->
                    ToggleButtonReports(
                        text = text,
                        isSelected = selectedOption.value == text,
                        onClick = { selectedOption.value = text }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedOption.value == "Asset History") {
                AssetPositionHistoryList()
            } else {
                AssetZoneHistoryList()
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    try {
                        generateReport(context, "PDF")
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("Reports", "Error generating PDF report", e)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "Generate PDF")
            }

            Button(
                onClick = {
                    try {
                        generateReport(context, "JSON")
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("Reports", "Error generating JSON report", e)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "Generate JSON")
            }
        }
    }
}

fun generateReport(context: Context, reportType: String) {
    val reportGenerator: IReportGenerator = when (reportType) {
        "PDF" -> PDFReportGenerator()
        "JSON" -> JSONReportGenerator()
        else -> throw IllegalArgumentException("Unsupported report type: $reportType")
    }

    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "report.${reportType.lowercase()}")

    val dataAsset = liveAssetPositionList
    val dataPositionHistory = assetPositionHistoryList
    val dataZoneHistory = assetZoneHistoryList

    if (dataAsset.isEmpty() && dataPositionHistory.isEmpty() && dataZoneHistory.isEmpty()) {
        Toast.makeText(context, "No data available to generate report", Toast.LENGTH_LONG).show()
        return
    }

    try {
        reportGenerator.saveReport(dataAsset, dataZoneHistory, dataPositionHistory, file)
        Toast.makeText(context, "$reportType saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error saving report: ${e.message}", Toast.LENGTH_LONG).show()
        Log.e("Reports", "Error saving report", e)
    }
}

@Preview
@Composable
fun PreviewReports() {
    JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
    JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
    JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
    Reports()
}