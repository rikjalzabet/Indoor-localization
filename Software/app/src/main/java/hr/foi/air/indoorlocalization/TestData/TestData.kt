package hr.foi.air.indoorlocalization.TestData

import hr.foi.air.indoorlocalization.models.IAsset
import hr.foi.air.indoorlocalization.models.IFloorMap
import hr.foi.air.indoorlocalization.models.IZone
import hr.foi.air.indoorlocalization.models.Point
import hr.foi.air.indoorlocalization.models.impl.Asset
import hr.foi.air.indoorlocalization.models.impl.FloorMap
import hr.foi.air.indoorlocalization.models.impl.Zone
import java.util.Date

class TestData {
    companion object {
        fun getAssets(): List<IAsset> {
            return listOf(
                Asset(1, "IAsset 1", 10.5f, 20.3f, Date(), 1, true),
                Asset(2, "IAsset 2", 15.7f, 25.8f, Date(), 1, false),
                Asset(3, "IAsset 3", 20.0f, 30.0f, Date(), 2, true)
            )
        }

        fun getFloorMaps(): List<IFloorMap> {
            return listOf(
                FloorMap(1, "Floor 1 (local)", "local_floor_map_test_1"),
                FloorMap(2, "Floor 2 (online)", "https://cdn.sick.com/media/content/he4/h01/10666047406110.jpg"),
            )
        }

        fun getZones(): List<IZone> {
            return listOf(
                Zone(
                    1, "IZone 1", listOf(
                        Point(10.5f, 20.3f, 1),
                        Point(15.7f, 25.8f, 2),
                        Point(20.0f, 30.0f, 3)
                    )
                ),
                Zone(
                    2, "IZone 2", listOf(
                        Point(5.5f, 10.3f, 1),
                        Point(12.0f, 15.8f, 2),
                        Point(18.2f, 22.5f, 3)
                    )
                )
            )
        }
    }
}
