package hr.foi.air.indoorlocalization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.ws.TestData.TestData
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
            val floorMap = floorMapList[0]//TestData.getFloorMaps()[0]
            JsonDataParser().updateZones(testDataJSONZones)
            JsonDataParser().updateLiveAssetPositions(testAssetPositionJSON)
            MapHome(floorMap = floorMap)
        }
        composable(BottomNavigationItem.Heatmap.route) {
            Heatmap()
        }
        composable(BottomNavigationItem.Reports.route) {
            Reports()
        }
    }
}