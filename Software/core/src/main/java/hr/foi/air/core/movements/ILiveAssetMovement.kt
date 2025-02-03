package hr.foi.air.core.movements

import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.MutableState
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.parser.liveAssetPositionList
import kotlin.math.floor

interface ILiveAssetMovement {

    suspend fun fetchLiveMovementData(): List<IAsset> {
        return liveAssetPositionList
    }

    suspend fun simulateLiveMovement(
        floorMapId: Int,
        assetPositions: MutableState<List<IAsset>>
    )
}