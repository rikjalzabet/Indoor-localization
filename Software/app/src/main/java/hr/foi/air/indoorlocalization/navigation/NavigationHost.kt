package hr.foi.air.indoorlocalization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.indoorlocalization.asset.HeatmapAssetLiveMovement
import hr.foi.air.indoorlocalization.asset.HomeMapAssetLiveMovement
import hr.foi.air.ws.TestData.*
import hr.foi.air.indoorlocalization.navigation.items.Heatmap
import hr.foi.air.indoorlocalization.navigation.items.MapHome
import hr.foi.air.indoorlocalization.navigation.items.Reports
import hr.foi.air.indoorlocalization.parser.*

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