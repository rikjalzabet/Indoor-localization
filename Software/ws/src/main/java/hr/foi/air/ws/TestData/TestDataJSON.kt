package hr.foi.air.ws.TestData


val testDataJSONZones = """
[
    {
        "id": 4,
        "name": "IZone 3",
        "points": [
            {"x": 0.2, "y": 0.2, "ordinalNumber": 1},
            {"x": 0.4, "y": 0.4, "ordinalNumber": 2},
            {"x": 0.2, "y": 0.4, "ordinalNumber": 3},
            {"x": 0.4, "y": 0.2, "ordinalNumber": 4}
        ]
    },
    {
        "id": 5,
        "name": "IZone 4",
        "points": [
            {"x": 0.1, "y": 0.6, "ordinalNumber": 1},
            {"x": 0.3, "y": 0.8, "ordinalNumber": 2},
            {"x": 0.1, "y": 0.8, "ordinalNumber": 3},
            {"x": 0.3, "y": 0.6, "ordinalNumber": 4}
        ]
    }
]
"""
val testDataJSONMap = """
    [
        {
            "id": 1,
            "name": "Floor 1 (local)",
            "image": "local_floor_map_test_1"
        },
        {
            "id": 2,
            "name": "Floor 2 (online)",
            "image": "https://cdn.sick.com/media/content/he4/h01/10666047406110.jpg"
        }
    ]
"""

val testAssetPositionJSON = """
[
    { "id": 1, "name": "Asset 1", "x": 0.2, "y": 0.2, "lastSync": "2024-12-02 00:00:00", "floorMapId": 1, "active": true },
    { "id": 2, "name": "Asset 2", "x": 0.5, "y": 0.4, "lastSync": "2024-12-02 00:00:05", "floorMapId": 1, "active": true },
    { "id": 3, "name": "Asset 3", "x": 0.1, "y": 0.1, "lastSync": "2024-12-02 00:00:10", "floorMapId": 1, "active": true },
    { "id": 4, "name": "Asset 4", "x": 0.01, "y": 0.01, "lastSync": "2024-12-02 00:00:15", "floorMapId": 1, "active": true },
    { "id": 5, "name": "Asset 1", "x": 0.2, "y": 0.2, "lastSync": "2024-12-02 00:00:20", "floorMapId": 1, "active": true },
    { "id": 6, "name": "Asset 2", "x": 0.5, "y": 0.4, "lastSync": "2024-12-02 00:00:25", "floorMapId": 1, "active": true },
    { "id": 7, "name": "Asset 3", "x": 0.1, "y": 0.1, "lastSync": "2024-12-02 00:00:30", "floorMapId": 1, "active": true },
    { "id": 8, "name": "Asset 4", "x": 0.01, "y": 0.01, "lastSync": "2024-12-02 00:00:35", "floorMapId": 1, "active": true },
    { "id": 9, "name": "Asset 1", "x": 0.2, "y": 0.2, "lastSync": "2024-12-02 00:00:40", "floorMapId": 1, "active": true },
    { "id": 10, "name": "Asset 2", "x": 0.5, "y": 0.4, "lastSync": "2024-12-02 00:00:45", "floorMapId": 1, "active": true },
    { "id": 11, "name": "Asset 3", "x": 0.1, "y": 0.1, "lastSync": "2024-12-02 00:00:50", "floorMapId": 1, "active": true },
    { "id": 12, "name": "Asset 4", "x": 0.01, "y": 0.01, "lastSync": "2024-12-02 00:00:55", "floorMapId": 1, "active": true },
    { "id": 13, "name": "Asset 1", "x": 0.2, "y": 0.2, "lastSync": "2024-12-02 00:00:60", "floorMapId": 1, "active": true },
    { "id": 14, "name": "Asset 2", "x": 0.5, "y": 0.4, "lastSync": "2024-12-02 00:01:00", "floorMapId": 1, "active": true },
    { "id": 15, "name": "Asset 3", "x": 0.1, "y": 0.1, "lastSync": "2024-12-02 00:01:05", "floorMapId": 1, "active": true },
    { "id": 16, "name": "Asset 4", "x": 0.01, "y": 0.01, "lastSync": "2024-12-02 00:01:10", "floorMapId": 1, "active": true },
    { "id": 17, "name": "Asset 1", "x": 0.2, "y": 0.2, "lastSync": "2024-12-02 00:01:15", "floorMapId": 1, "active": true },
    { "id": 18, "name": "Asset 2", "x": 0.5, "y": 0.4, "lastSync": "2024-12-02 00:01:20", "floorMapId": 1, "active": true },
    { "id": 19, "name": "Asset 3", "x": 0.1, "y": 0.1, "lastSync": "2024-12-02 00:01:25", "floorMapId": 1, "active": true },
    { "id": 20, "name": "Asset 4", "x": 0.01, "y": 0.01, "lastSync": "2024-12-02 00:01:30", "floorMapId": 1, "active": true }
]
"""

val assetPositionHistoryJSON = """
    [
        {
            "id": 1,
            "assetId": 101,
            "x": 12.34,
            "y": 56.78,
            "dateTime": "2025-01-01T10:00:00Z",
            "floorMapId": 1
        },
        {
            "id": 2,
            "assetId": 102,
            "x": 98.76,
            "y": 54.32,
            "dateTime": "2025-01-02T11:30:00Z",
            "floorMapId": 2
        }
    ]
"""

val assetZoneHistoryJSON = """
    [
        {
            "id": 1,
            "assetId": 101,
            "zoneId": 201,
            "enterDateTime": "2025-01-01T09:00:00Z",
            "exitDateTime": "2025-01-01T10:00:00Z",
            "retentionTime": 3600
        },
        {
            "id": 2,
            "assetId": 102,
            "zoneId": 202,
            "enterDateTime": "2025-01-02T11:00:00Z",
            "exitDateTime": null,
            "retentionTime": 1800
        }
    ]
"""