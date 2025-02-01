package hr.foi.air.core.models

import java.util.Date

interface IAssetZoneHistory {
    val id: Int
    val assetId: Int
    val zoneId: Int
    val enterDateTime: String
    val exitDateTime: String?
    val retentionTime: Long
}