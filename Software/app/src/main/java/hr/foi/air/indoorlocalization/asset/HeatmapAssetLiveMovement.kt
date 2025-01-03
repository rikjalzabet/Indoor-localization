package hr.foi.air.indoorlocalization.asset

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import ca.hss.heatmaplib.HeatMap
import hr.foi.air.core.movements.ILiveAssetMovement
import hr.foi.air.core.parser.liveAssetPositionList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class HeatmapAssetLiveMovement(): ILiveAssetMovement {
    override suspend fun simulateLiveMovement(
        currentPosition: MutableState<Offset>,
        floorMapId: Int
    ) {
        val liveAssetPositions = liveAssetPositionList.filter { it.floorMapId == floorMapId }

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
        }
    }
}
