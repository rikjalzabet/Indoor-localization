package hr.foi.air.indoorlocalization.asset

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import ca.hss.heatmaplib.HeatMap
import com.google.gson.Gson
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.core.parser.liveAssetPositionList
import hr.foi.air.indoorlocalization.parser.JsonDataParser
import hr.foi.air.ws.getApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class HeatmapAssetLiveMovement(): ILiveAssetMovement {
    override suspend fun simulateLiveMovement(
        currentPosition: MutableState<Offset>,
        floorMapId: Int
    ) {
        /*val liveAssetPositions = liveAssetPositionList.filter { it.floorMapId == floorMapId }

        if (liveAssetPositions.isEmpty()) {
            Log.e("AssetLiveMovement", "No positions found for floor map $floorMapId")
            return
        }
        var index = 0
        while (true) {
            val assetPosition = liveAssetPositions[index]
            withContext(Dispatchers.Main) {
                currentPosition.value = Offset(assetPosition.x, assetPosition.y)
            }
            index = (index + 1) % liveAssetPositions.size
            delay(3000L)
        }*/
        val apiService = getApiService()
        val gson = Gson()
        while (true) {
            try {
                val getLiveMovement = withContext(Dispatchers.IO) {
                    apiService.getAllAssets()
                }
                val getLiveMovementToJson = gson.toJson(getLiveMovement)
                withContext(Dispatchers.Main){
                    liveAssetPositionList.clear()
                    JsonDataParser().updateLiveAssetPositions(getLiveMovementToJson)
                }
                val liveAssetPositions = liveAssetPositionList.filter { it.floorMapId == floorMapId }

                if (liveAssetPositions.isNotEmpty()) {
                    val assetPosition = liveAssetPositions.first()
                    withContext(Dispatchers.Main) {
                        currentPosition.value = Offset(assetPosition.x, assetPosition.y)
                    }
                } else {
                    Log.e("HomeMapLiveMov88", "No positions found for floor map $floorMapId")
                }
            } catch (e: Exception) {
                Log.e("HomeMapLiveMov88", "Error fetching live movement data: ${e.message}")
            }

            delay(1500L)
        }
    }
}
