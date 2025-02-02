package hr.foi.air.indoorlocalization.navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import hr.foi.air.core.models.IFloorMap
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.core.parser.*
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor

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
            /*DisposableEffect(Unit) {
                onDispose {
                    liveAssetPositionList.clear()
                }
            }*/
            //USE THIS FOR LOCAL TEST DATA
            /*JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            fetchAndLogAssets()
            MapHome(floorMap = floorMap, HomeMapAssetLiveMovement())*/
            //USE THIS FOR LIVE TEST DATA
            var floorMap = remember { mutableStateOf<IFloorMap?>(null) }
            var isDataLoaded = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                /*
                Keep in mind, that every floorMapId has to have at least 1 asset.
                Example json on "server":
                Asset:[
                  {
                    id: Math.floor(Math.random()),
                    name: "Asset" + Math.floor(Math.random() * 100.0),
                    x: 0.2,
                    y: 0.5,
                    lastSync: new Date().toISOString(),
                    floorMapId: 2,
                    active: true,
                  },
                  {
                    id: Math.floor(Math.random()),
                    name: "Asset" + Math.floor(Math.random() * 100.0),
                    x: Number((Math.random() * 2.0).toFixed(1)),
                    y: Number((Math.random() * 2.0).toFixed(1)),
                    lastSync: new Date().toISOString(),
                    floorMapId: 1,
                    active: true,
                  }
                ]
                */
                try {
                    val apiService = getApiService()
                    val gson = Gson()
                    clearDataListsIfTheyNotEmpty()

                    //Get zones
                    val getZones = withContext(Dispatchers.IO) {
                        apiService.getAllZones()
                    }
                    val getZonesToJson = gson.toJson(getZones)
                    Log.d("ApiService44", "Fetched Zones: $getZonesToJson")
                    //get maps
                    val getMaps = withContext(Dispatchers.IO) {
                        apiService.getAllFloorMaps()
                    }
                    val getMapsToJson = gson.toJson(getMaps)
                    Log.d("ApiService44", "Fetched Maps: $getMapsToJson")
                    //get live movement
                    val getLiveMovement = withContext(Dispatchers.IO) {
                        apiService.getAllAssets()
                    }
                    val getLiveMovementToJson = gson.toJson(getLiveMovement)
                    Log.d("ApiService44", "Fetched Live Movement: $getLiveMovementToJson")

                    JsonDataParser().updateZones(getZonesToJson)
                    JsonDataParser().updateFloorMaps(getMapsToJson)
                    JsonDataParser().updateLiveAssetPositions(getLiveMovementToJson)

                    delay(3000L)

                    if (floorMapList.size > 1) {
                        floorMap.value = floorMapList[1]
                        isDataLoaded.value = true
                    } else {
                        Log.e("ApiService", "floorMapList is empty or too small!")
                    }
                } catch (e: Exception) {
                    Log.e("ApiService", "Error fetching data: ${e.message}")
                }
            }
            if (!isDataLoaded.value) {
                CircularProgressIndicator()
            } else {
                floorMap?.let {
                    fetchAndLogAssets()
                    MapHome(floorMap = floorMap.value!!, HomeMapAssetLiveMovement())
                }
            }
        }
        composable(BottomNavigationItem.Heatmap.route) {
            /*JsonDataParser().updateFloorMaps(testDataJSONMap)
            val floorMap = floorMapList[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            Heatmap(floorMap = floorMap, HeatmapAssetLiveMovement())*/
            var floorMap = remember { mutableStateOf<IFloorMap?>(null) }
            var isDataLoaded = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                try {
                    val apiService = getApiService()
                    val gson = Gson()
                    clearDataListsIfTheyNotEmpty()

                    //Get zones
                    val getZones = withContext(Dispatchers.IO) {
                        apiService.getAllZones()
                    }
                    val getZonesToJson = gson.toJson(getZones)
                    Log.d("ApiService551", "Fetched Zones: $getZonesToJson")
                    //get maps
                    val getMaps = withContext(Dispatchers.IO) {
                        apiService.getAllFloorMaps()
                    }
                    val getMapsToJson = gson.toJson(getMaps)
                    Log.d("ApiService551", "Fetched Maps: $getMapsToJson")
                    //get live movement - live heatmap
                    val getLiveMovement = withContext(Dispatchers.IO) {
                        apiService.getAllAssets()
                    }
                    val getLiveMovementToJson = gson.toJson(getLiveMovement)
                    Log.d("ApiService551", "Fetched Live Movement: $getLiveMovementToJson")
                    //get position history - history heatmap
                    val assetPositionHistory = withContext(Dispatchers.IO) {
                        apiService.getAllAssetPositionHistory()
                    }
                    val assetPositionHistoryJson = gson.toJson(assetPositionHistory)
                    Log.d("ApiService551", "Fetched PosHist: $assetPositionHistoryJson")
                    //transform data
                    JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJson)
                    JsonDataParser().updateZones(getZonesToJson)
                    JsonDataParser().updateFloorMaps(getMapsToJson)
                    JsonDataParser().updateLiveAssetPositions(getLiveMovementToJson)

                    delay(3000L)
                    if (floorMapList.size > 1) {
                        floorMap.value = floorMapList[1]
                        isDataLoaded.value = true
                    } else {
                        Log.e("ApiService", "floorMapList is empty or too small!")
                    }
                } catch (e: Exception) {
                    Log.e("ApiService", "Error fetching data: ${e.message}")
                }
            }
            if (!isDataLoaded.value) {
                CircularProgressIndicator() // Or any other loading UI
            } else {
                floorMap?.let {
                    Heatmap(floorMap = floorMap.value!!, HeatmapAssetLiveMovement())
                }
            }
        }
        composable(BottomNavigationItem.Reports.route) {

            //USE THIS FOR LOCAL TEST DATA
            /*JsonDataParser().updateAssetZoneHistory(assetZoneHistoryJSON)
            JsonDataParser().updateAssetPositionHistory(assetPositionHistoryJSON)
            Reports()*/

            //USE THIS FOR FETCHING DATA FROM API
            LaunchedEffect(Unit) {
                val apiService = getApiService()
                val gson = Gson()

                clearDataListsIfTheyNotEmpty()

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
fun clearDataListsIfTheyNotEmpty() {
    if (floorMapList.isNotEmpty()
        || zonesList.isNotEmpty()
        || liveAssetPositionList.isNotEmpty()
        || assetPositionHistoryList.isNotEmpty()
        || assetZoneHistoryList.isNotEmpty()) {
        floorMapList.clear()
        zonesList.clear()
        liveAssetPositionList.clear()
        assetPositionHistoryList.clear()
        assetZoneHistoryList.clear()
    }
}