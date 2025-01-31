package hr.foi.air.ws

import hr.foi.air.core.models.impl.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("Assets")
    suspend fun getAllAssets(): List<Asset>

    @GET("AssetPositionHistory")
    suspend fun getAllAssetPositionHistory(): List<AssetPositionHistory>

    @GET("AssetZoneHistory")
    suspend fun getAllAssetZoneHistory(): List<AssetZoneHistory>

    @GET("FloorMaps")
    suspend fun getAllFloorMaps(): List<FloorMap>

    @GET("Zones")
    suspend fun getAllZones(): List<Zone>
}

fun getApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:7088/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}