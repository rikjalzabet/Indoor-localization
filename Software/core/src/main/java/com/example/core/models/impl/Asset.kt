package com.example.core.models.impl

import com.example.core.models.IAsset
import java.util.Date

class Asset(
    override val id: Int,
    override val name: String,
    override val x: Float,
    override val y: Float,
    override val lastSync: Date,
    override val floorMapId: Int,
    override val active: Boolean
): IAsset {
}