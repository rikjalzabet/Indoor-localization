package hr.foi.air.indoorlocalization.TestData

import com.example.core.models.IAsset
import com.example.core.models.IFloorMap
import com.example.core.models.IZone
import com.example.core.models.Point
import com.example.core.models.impl.Asset
import com.example.core.models.impl.FloorMap
import com.example.core.models.impl.Zone
import hr.foi.air.indoorlocalization.parser.zonesList
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
                    /* Zone(
                         2, "IZone 1", listOf(
                             Point(0.2f, 0.2f, 1),
                             Point(0.4f, 0.4f, 2),
                             Point(0.2f, 0.4f, 3),
                             Point(0.4f, 0.2f, 4)
                         )
                     ),*/
                    Zone(
                        3, "IZone 2", listOf(
                            Point(0.1f, 0.1f, 1),
                            Point(0.5f, 0.1f, 2),
                            Point(0.5f, 0.5f, 3),
                            Point(0.1f, 0.5f, 4)
                        )
                    )
                )
            }
        fun addZonesToList(){
            for (zone in getZones()){
                zonesList.add(zone)
            }
        }
    }
}
