package hr.foi.air.indoorlocalization.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.withContext

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
            //USE THIS FOR LOCAL TEST DATA
            /*JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            fetchAndLogAssets()
            MapHome(floorMap = floorMap, HomeMapAssetLiveMovement())*/
            //USE THIS FOR LIVE TEST DATA (for now only zones)
            LaunchedEffect(Unit) {
                val apiService = getApiService()
                val gson = Gson()

                val getZones = withContext(Dispatchers.IO) {
                    apiService.getAllZones()
                }
                val getZonesToJson = gson.toJson(getZones)
                Log.d("ApiService321", "Fetched ZoneHist: $getZonesToJson")

                JsonDataParser().updateZones(getZonesToJson)
            }

            JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
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
            /*
            //USE THIS FOR LOCAL TEST DATA
            JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
            JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
            Reports()*/

            //USE THIS FOR FETCHING DATA FROM API
            LaunchedEffect(Unit) {
                val apiService = getApiService()
                val gson = Gson()

                val assetZoneHistory = withContext(Dispatchers.IO) {
                    apiService.getAllAssetZoneHistory()
                }
                val assetZoneHistoryJson = gson.toJson(assetZoneHistory)
                Log.d("ApiService321", "Fetched ZoneHist: $assetZoneHistoryJson")
                val assetPositionHistory = withContext(Dispatchers.IO) {
                    apiService.getAllAssetPositionHistory()
                }
                val assetPositionHistoryJson = gson.toJson(assetPositionHistory)
                Log.d("ApiService321", "Fetched PosHist: $assetPositionHistoryJson")

                JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJson)
                JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJson)
            }
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