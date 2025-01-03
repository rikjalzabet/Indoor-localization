package hr.foi.air.core.parser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IFloorMap
import hr.foi.air.core.models.IZone


val floorMapList = mutableStateListOf<IFloorMap>()
val zonesList = mutableStateListOf<IZone>()
val liveAssetPositionList = mutableStateListOf<IAsset>()




