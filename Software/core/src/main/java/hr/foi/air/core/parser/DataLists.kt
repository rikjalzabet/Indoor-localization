package hr.foi.air.core.parser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IAssetPositionHistory
import hr.foi.air.core.models.IAssetZoneHistory
import hr.foi.air.core.models.IFloorMap
import hr.foi.air.core.models.IZone


val floorMapList = mutableStateListOf<IFloorMap>()
val zonesList = mutableStateListOf<IZone>()
val liveAssetPositionList = mutableStateListOf<IAsset>()
val assetPositionHistoryList = mutableStateListOf<IAssetPositionHistory>()
val assetZoneHistoryList =  mutableStateListOf<IAssetZoneHistory>()

val historyAssetPositionList = mutableStateListOf<IAssetPositionHistory>()




