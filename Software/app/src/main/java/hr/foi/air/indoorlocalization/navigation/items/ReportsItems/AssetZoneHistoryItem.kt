package hr.foi.air.indoorlocalization.navigation.items.ReportsItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import hr.foi.air.core.models.*
import androidx.compose.ui.Modifier
import hr.foi.air.core.parser.assetZoneHistoryList

@Composable
fun AssetZoneHistoryItem(history: IAssetZoneHistory) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Enter Date Time: ${history.enterDateTime}", style = MaterialTheme.typography.bodyMedium)
        Text("Exit Date Time: ${history.exitDateTime ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
        Text("Retention Time: ${history.retentionTime} seconds", style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun AssetZoneHistoryList() {
    val historyList = remember { assetZoneHistoryList }
    LazyColumn {
        for(historyItem in historyList) {
            item(historyItem.id){
                AssetZoneHistoryItem(historyItem)
            }
        }
    }
}

