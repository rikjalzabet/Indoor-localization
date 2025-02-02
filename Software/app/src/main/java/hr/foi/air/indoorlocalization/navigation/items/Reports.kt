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

        // Use a Row to arrange the buttons horizontally
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
                        generatePdfReport(context)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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
                        generateJsonReport(context)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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

fun generateJsonReport(context: Context) {
    val jsonReportGenerator = JSONReportGenerator()
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "report.json")

    val jsonDataParser = JsonDataParser()
    jsonDataParser.updateLiveAssetPositions(testAssetPositionJSON)
    jsonDataParser.updateAssetPositionHistory(assetPositionHistoryJSON)
    jsonDataParser.updateAssetZoneHistory(assetZoneHistoryJSON)

    val dataAsset = liveAssetPositionList
    val dataPositionHistory = assetPositionHistoryList
    val dataZoneHistory = assetZoneHistoryList

    jsonReportGenerator.saveReport(dataAsset, dataZoneHistory, dataPositionHistory, file)
    Toast.makeText(context, "JSON saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
}

fun generatePdfReport(context: Context) {
    val pdfReportGenerator = PDFReportGenerator()
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "report.pdf")

    /*val dataAsset: List<Asset> = JsonDataParser().parseLiveAssetPositions(testAssetPositionJSON)
    val dataPositionHistory: List<AssetPositionHistory> = JsonDataParser().parseAssetPositionHistory(assetPositionHistoryJSON)
    val dataZoneHistory: List<AssetZoneHistory> = JsonDataParser().parseAssetZoneHistory(assetZoneHistoryJSON)*/

    val jsonDataParser = JsonDataParser()
    jsonDataParser.updateLiveAssetPositions(testAssetPositionJSON)
    jsonDataParser.updateAssetPositionHistory(assetPositionHistoryJSON)
    jsonDataParser.updateAssetZoneHistory(assetZoneHistoryJSON)

    val dataAsset = liveAssetPositionList
    val dataPositionHistory = assetPositionHistoryList
    val dataZoneHistory = assetZoneHistoryList

    pdfReportGenerator.saveReport(dataAsset, dataZoneHistory, dataPositionHistory, file)
    Toast.makeText(context, "PDF saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
}

@Preview
@Composable
fun PreviewReports() {
    JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
    JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
    Reports()
}