package hr.foi.air.indoorlocalization.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.indoorlocalization.asset.HeatmapAssetLiveMovement
import hr.foi.air.indoorlocalization.asset.HomeMapAssetLiveMovement
import hr.foi.air.ws.TestData.*
import hr.foi.air.indoorlocalization.navigation.items.Heatmap
import hr.foi.air.indoorlocalization.navigation.items.MapHome
import hr.foi.air.indoorlocalization.navigation.items.Reports
import hr.foi.air.indoorlocalization.parser.*
import hr.foi.air.ws.getApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Map.route,
        modifier = modifier
    ) {
        composable(BottomNavigationItem.Map.route) {
            JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            fetchAndLogAssets()
            MapHome(floorMap = floorMap, HomeMapAssetLiveMovement())
        }
        composable(BottomNavigationItem.Heatmap.route) {
            JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            Heatmap(floorMap = floorMap, HeatmapAssetLiveMovement())
        }
        composable(BottomNavigationItem.Reports.route) {
            JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
            JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
            Reports()
        }
    }
}
fun fetchAndLogAssets() {
    val apiService = getApiService()
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val assets = apiService.getAllAssets()
            val gson = Gson()
            val assetsJson = gson.toJson(assets)
            Log.d("ApiService123", "Fetched assets: $assetsJson")
        } catch (e: Exception) {
            Log.e("ApiService123", "Error fetching assets", e)
        }
    }
}