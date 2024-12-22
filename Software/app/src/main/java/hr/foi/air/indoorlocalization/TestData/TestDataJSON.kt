package hr.foi.air.indoorlocalization.TestData


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