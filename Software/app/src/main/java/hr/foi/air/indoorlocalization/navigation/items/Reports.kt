package hr.foi.air.indoorlocalization.navigation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.indoorlocalization.navigation.items.ReportsItems.AssetPositionHistoryList
import hr.foi.air.indoorlocalization.navigation.items.ReportsItems.AssetZoneHistoryList
import hr.foi.air.indoorlocalization.parser.JsonDataParser
import hr.foi.air.ws.TestData.assetPositionHistoryJSON
import hr.foi.air.ws.TestData.assetZoneHistoryJSON

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
    val radioOptions = listOf("Asset History","Zone History")
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            ,
            horizontalArrangement = Arrangement.SpaceEvenly){
            radioOptions.forEach { text ->
                ToggleButtonReports(
                    text = text,
                    isSelected = selectedOption.value == text,
                    onClick = {
                        selectedOption.value = text
                    }
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
}

@Preview
@Composable
fun PreviewReports() {
    JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
    JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
    Reports()
}