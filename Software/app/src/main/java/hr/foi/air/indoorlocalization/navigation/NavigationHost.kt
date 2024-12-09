package hr.foi.air.indoorlocalization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hr.foi.air.indoorlocalization.TestData.TestData
import hr.foi.air.indoorlocalization.TestData.testDataJSONMap
import hr.foi.air.indoorlocalization.TestData.testDataJSONZones
import hr.foi.air.indoorlocalization.navigation.items.Heatmap
import hr.foi.air.indoorlocalization.navigation.items.MapHome
import hr.foi.air.indoorlocalization.navigation.items.Reports
import hr.foi.air.indoorlocalization.parser.floorMapList
import hr.foi.air.indoorlocalization.parser.updateFloorMapsFromJson
import hr.foi.air.indoorlocalization.parser.updateZonesFromJson

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
            updateFloorMapsFromJson(testDataJSONMap)
            val floorMap = floorMapList[0]//TestData.getFloorMaps()[0]
            updateZonesFromJson(testDataJSONZones)
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