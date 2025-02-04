package hr.foi.air.indoorlocalization.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(
    val name: String,
    val route: String,
    val icon: ImageVector
){
    object Map: BottomNavigationItem("Map","map_home", Icons.Default.Home )
    object Heatmap: BottomNavigationItem("Heatmap","heatmap", Icons.Default.Star )
    object Reports: BottomNavigationItem("Reports","object_reports", Icons.Default.List )

}
