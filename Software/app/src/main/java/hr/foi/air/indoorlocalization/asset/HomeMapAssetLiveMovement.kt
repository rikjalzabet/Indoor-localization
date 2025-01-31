package hr.foi.air.indoorlocalization.asset

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import hr.foi.air.core.parser.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.indoorlocalization.navigation.clearDataListsIfTheyNotEmpty
import hr.foi.air.indoorlocalization.parser.JsonDataParser
import hr.foi.air.ws.getApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeMapAssetLiveMovement(): ILiveAssetMovement {
    override suspend fun simulateLiveMovement(
        currentPosition: MutableState<Offset>,
        floorMapId: Int
    ) {
        /*var liveAssetPositions = liveAssetPositionList.filter { it.floorMapId == floorMapId }

        if (liveAssetPositions.isEmpty()) {
            Log.e("AssetLiveMovement", "No positions found for floor map $floorMapId")
            return
        }

        var index = 0

                val assetPosition = liveAssetPositions[index]
                //Log.i("AssetLiveMovement", "Moving asset ${assetPosition.id} to position (${assetPosition.x}, ${assetPosition.y})")
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
                // Fetch live movement data
                val getLiveMovement = withContext(Dispatchers.IO) {
                    apiService.getAllAssets()
                }
                val getLiveMovementToJson = gson.toJson(getLiveMovement)
                Log.d("HomeMapLiveMov88", "Fetched NEW Live Movement: $getLiveMovementToJson")
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
