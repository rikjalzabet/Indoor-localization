-- Generate some floorMaps records
INSERT INTO floorMaps (name, image) VALUES
('FloorMap1', 'https://wpmedia.roomsketcher.com/content/uploads/2022/01/06145940/What-is-a-floor-plan-with-dimensions.png'),
('FloorMap2', 'https://wpmedia.roomsketcher.com/content/uploads/2022/01/06150346/2-Bedroom-Home-Plan-With-Dimensions.png');

-- Generate some assets records
INSERT INTO assets (name, x, y, lastSync, floorMapId, active) VALUES
('Forklift_001', 15.345678, 25.987654, NOW() - INTERVAL '5 seconds', 1, TRUE),
('Palette_102', 30.654321, 40.123456, NOW() - INTERVAL '10 seconds', 2, FALSE),
('Box_345', 5.123456, 10.987654, NOW() - INTERVAL '15 seconds', 1, FALSE),
('Device_901', 50.765432, 60.876543, NOW() - INTERVAL '20 seconds', 2, TRUE),
('Palette_203', 70.543210, 80.678901, NOW() - INTERVAL '25 seconds', 1, TRUE);

-- Generate some zones records
INSERT INTO zones (name, points) VALUES
('Zone_1', '[{"x": 0.0, "y": 0.0}, {"x": 33.33, "y": 0.0}, {"x": 33.33, "y": 50.0}, {"x": 0.0, "y": 50.0}]'),
('Zone_2', '[{"x": 33.33, "y": 0.0}, {"x": 66.67, "y": 0.0}, {"x": 66.67, "y": 50.0}, {"x": 33.33, "y": 50.0}]'),
('Zone_3', '[{"x": 66.67, "y": 0.0}, {"x": 100.0, "y": 0.0}, {"x": 100.0, "y": 50.0}, {"x": 66.67, "y": 50.0}]'),
('Zone_4', '[{"x": 0.0, "y": 50.0}, {"x": 33.33, "y": 50.0}, {"x": 33.33, "y": 100.0}, {"x": 0.0, "y": 100.0}]'),
('Zone_5', '[{"x": 33.33, "y": 50.0}, {"x": 66.67, "y": 50.0}, {"x": 66.67, "y": 100.0}, {"x": 33.33, "y": 100.0}]'),
('Zone_6', '[{"x": 66.67, "y": 50.0}, {"x": 100.0, "y": 50.0}, {"x": 100.0, "y": 100.0}, {"x": 66.67, "y": 100.0}]');

-- Generate some assetPositionHistory records
INSERT INTO assetPositionHistory (assetId, x, y, dateTime, floorMapId) VALUES
(1, 15.456789, 26.123456, NOW() - INTERVAL '1 minute', 1),
(2, 30.987654, 40.654321, NOW() - INTERVAL '2 minutes', 2),
(3, 5.543210, 11.234567, NOW() - INTERVAL '3 minutes', 1),
(4, 50.876543, 61.123456, NOW() - INTERVAL '4 minutes', 2),
(5, 70.654321, 81.345678, NOW() - INTERVAL '5 minutes', 1),
(1, 16.123456, 27.987654, NOW() - INTERVAL '6 minutes', 1),
(2, 31.654321, 41.123456, NOW() - INTERVAL '7 minutes', 2),
(3, 6.234567, 12.876543, NOW() - INTERVAL '8 minutes', 1),
(4, 51.543210, 62.234567, NOW() - INTERVAL '9 minutes', 2),
(5, 71.345678, 82.456789, NOW() - INTERVAL '10 minutes', 1);

-- Generate some assetZoneHistory records
INSERT INTO assetZoneHistory (assetId, zoneId, enterDateTime, exitDateTime, retentionTime) VALUES
(1, 1, NOW() - INTERVAL '20 minutes', NOW() - INTERVAL '15 minutes', '5 minutes'),
(2, 2, NOW() - INTERVAL '25 minutes', NOW() - INTERVAL '18 minutes', '7 minutes'),
(3, 3, NOW() - INTERVAL '30 minutes', NOW() - INTERVAL '22 minutes', '8 minutes'),
(4, 4, NOW() - INTERVAL '35 minutes', NOW() - INTERVAL '28 minutes', '7 minutes'),
(5, 5, NOW() - INTERVAL '40 minutes', NOW() - INTERVAL '30 minutes', '10 minutes'),
(1, 2, NOW() - INTERVAL '50 minutes', NOW() - INTERVAL '42 minutes', '8 minutes'),
(2, 3, NOW() - INTERVAL '55 minutes', NOW() - INTERVAL '47 minutes', '8 minutes'),
(3, 4, NOW() - INTERVAL '60 minutes', NOW() - INTERVAL '53 minutes', '7 minutes'),
(4, 5, NOW() - INTERVAL '65 minutes', NOW() - INTERVAL '58 minutes', '7 minutes'),
(5, 1, NOW() - INTERVAL '70 minutes', NOW() - INTERVAL '62 minutes', '8 minutes');