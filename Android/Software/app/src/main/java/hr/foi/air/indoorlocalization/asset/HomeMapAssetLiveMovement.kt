package hr.foi.air.indoorlocalization.asset

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import hr.foi.air.core.models.IAsset
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
        floorMapId: Int,
        assetPositions: MutableState<List<IAsset>>
    ) {
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

                // Filter the assets for the current floor map and update the state
                val liveAssetPositions = getLiveMovement.filter { it.floorMapId == floorMapId }
                withContext(Dispatchers.Main) {
                    assetPositions.value = liveAssetPositions
                }
            } catch (e: Exception) {
                Log.e("HomeMapLiveMov88", "Error fetching live movement data: ${e.message}")
            }

            delay(1500L)
        }
    }
}
