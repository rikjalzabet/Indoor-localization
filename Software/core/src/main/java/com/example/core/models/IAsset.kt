package com.example.core.models

import java.util.Date

interface IAsset {
    val id: Int
    val name: String
    val x: Float
    val y: Float
    val lastSync: Date
    val floorMapId: Int
    val active: Boolean
}