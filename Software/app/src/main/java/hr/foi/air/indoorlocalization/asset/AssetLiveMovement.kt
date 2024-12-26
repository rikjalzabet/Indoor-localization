package hr.foi.air.indoorlocalization.asset

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay
import androidx.compose.runtime.MutableState
import hr.foi.air.core.parser.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun simulateLiveMovement(
    currentPosition: MutableState<Offset>,
    floorMapId: Int
) {
    /*val positions = listOf(
        Offset(0.1f, 0.1f),
        Offset(0.5f, 0.1f),
        Offset(0.5f, 0.5f),
        Offset(0.1f, 0.5f)
    )
    var index = 0
    while (true) {
        withContext(Dispatchers.Main) {
            currentPosition.value = positions[index]
        }
        index = (index + 1) % positions.size
        delay(3000L)
    }
     */
    val liveAssetPositions = liveAssetPositionList.filter { it.floorMapId == floorMapId }

    if (liveAssetPositions.isEmpty()) {
        Log.e("AssetLiveMovement", "No positions found for floor map $floorMapId")
        return
    }

    Log.i("AssetLiveMovement", "Found ${liveAssetPositions.size} asset positions for floor map $floorMapId")

    var index = 0
    while (true) {
        val assetPosition = liveAssetPositions[index]
        Log.i("AssetLiveMovement", "Moving asset ${assetPosition.id} to position (${assetPosition.x}, ${assetPosition.y})")
        withContext(Dispatchers.Main) {
            currentPosition.value = Offset(assetPosition.x, assetPosition.y)
        }
        index = (index + 1) % liveAssetPositions.size
        delay(3000L)
    }
}