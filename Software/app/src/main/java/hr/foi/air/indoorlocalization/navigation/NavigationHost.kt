package hr.foi.air.indoorlocalization.navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.delay
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
            //JsonDataParser().updateFloorMaps(testDataJSONMap)
           // var floorMap: IFloorMap
            var floorMap = remember { mutableStateOf<IFloorMap?>(null) }
            var isDataLoaded = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                /*val apiService = getApiService()
                val gson = Gson()

                val getZones = withContext(Dispatchers.IO) {
                    apiService.getAllZones()
                }
                val getZonesToJson = gson.toJson(getZones)
                Log.d("ApiService333", "Fetched ZoneHist: $getZonesToJson")

                val getMaps = withContext(Dispatchers.IO) {
                    apiService.getAllFloorMaps()
                }
                val getMapsToJson = gson.toJson(getMaps)
                Log.d("ApiService333", "Fetched Maps: $getMapsToJson")


                JsonDataParser().updateZones(getZonesToJson)
                JsonDataParser().updateFloorMaps(getMapsToJson)
                Log.d("ApiService333", "SAved Maps: $floorMapList")

                if (floorMapList.isNotEmpty()) {
                    floorMap.value = floorMapList[1] // Assign the fetched floorMap
                }*/
                try {
                    val apiService = getApiService()
                    val gson = Gson()

                    val getZones = withContext(Dispatchers.IO) {
                        apiService.getAllZones()
                    }
                    val getZonesToJson = gson.toJson(getZones)
                    Log.d("ApiService44", "Fetched Zones: $getZonesToJson")

                    val getMaps = withContext(Dispatchers.IO) {
                        apiService.getAllFloorMaps() // Ensure this actually fetches maps
                    }
                    val getMapsToJson = gson.toJson(getMaps)
                    Log.d("ApiService44", "Fetched Maps: $getMapsToJson")

                    JsonDataParser().updateZones(getZonesToJson)
                    JsonDataParser().updateFloorMaps(getMapsToJson)

                    delay(3000L)

                    if (floorMapList.size > 1) {
                        floorMap.value = floorMapList[1]
                        isDataLoaded.value = true // Mark data as loaded
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
                    JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
                    fetchAndLogAssets()
                    MapHome(floorMap = floorMap.value!!, HomeMapAssetLiveMovement())
                }
            }

            /*JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            fetchAndLogAssets()

            floorMap?.let {
                MapHome(floorMap = floorMap.value!!, HomeMapAssetLiveMovement())
            }*/
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