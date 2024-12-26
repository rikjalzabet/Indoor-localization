package hr.foi.air.core.movements

import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.MutableState

interface ILiveAssetMovement {
    suspend fun simulateLiveMovement(
        currentPosition: MutableState<Offset>,
        floorMapId: Int
    )
}