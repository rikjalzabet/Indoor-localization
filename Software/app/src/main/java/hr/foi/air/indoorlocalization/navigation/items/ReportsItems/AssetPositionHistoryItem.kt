package hr.foi.air.indoorlocalization.navigation.items.ReportsItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import hr.foi.air.core.models.*
import androidx.compose.ui.Modifier
import hr.foi.air.core.parser.assetPositionHistoryList

@Composable
fun AssetPositionHistoryItem(history: IAssetPositionHistory) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("X: ${history.x}, Y: ${history.y}", style = MaterialTheme.typography.bodyMedium)
        Text("Date Time: ${history.dateTime}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AssetPositionHistoryList() {
    val historyList = remember { assetPositionHistoryList }
    LazyColumn {
        for(historyItem in historyList) {
            item(historyItem.id){
                AssetPositionHistoryItem(historyItem)
            }
        }
    }
}

