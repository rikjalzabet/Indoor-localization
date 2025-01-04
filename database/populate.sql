-- Generate some floorMaps records
INSERT INTO floorMaps (name, image) VALUES
('FloorMap944', 'https://picsum.photos/id/944/500/500'),
('FloorMap343', 'https://picsum.photos/id/343/500/500'),
('FloorMap69', 'https://picsum.photos/id/69/500/500'),
('FloorMap879', 'https://picsum.photos/id/879/500/500'),
('FloorMap333', 'https://picsum.photos/id/333/500/500');

-- Generate some assets records
INSERT INTO assets (name, x, y, lastSync, floorMapId, active) VALUES
('Forklift_001', 15.345678, 25.987654, NOW() - INTERVAL '5 seconds', 1, TRUE),
('Palette_102', 30.654321, 40.123456, NOW() - INTERVAL '10 seconds', 2, TRUE),
('Box_345', 5.123456, 10.987654, NOW() - INTERVAL '15 seconds', 3, TRUE),
('Device_901', 50.765432, 60.876543, NOW() - INTERVAL '20 seconds', 4, TRUE),
('Palette_203', 70.543210, 80.678901, NOW() - INTERVAL '25 seconds', 5, TRUE);

-- Generate some zones records
INSERT into zones (name, points) VALUES
('Zone_1', '[{"x": 10.123456, "y": 20.987654}, {"x": 15.123456, "y": 25.987654}, {"x": 20.123456, "y": 30.987654}, {"x": 25.123456, "y": 35.987654}]'),
('Zone_2', '[{"x": 30.123456, "y": 40.987654}, {"x": 35.123456, "y": 45.987654}, {"x": 40.123456, "y": 50.987654}, {"x": 45.123456, "y": 55.987654}]'),
('Zone_3', '[{"x": 50.123456, "y": 60.987654}, {"x": 55.123456, "y": 65.987654}, {"x": 60.123456, "y": 70.987654}, {"x": 65.123456, "y": 75.987654}]'),
('Zone_4', '[{"x": 70.123456, "y": 80.987654}, {"x": 75.123456, "y": 85.987654}, {"x": 80.123456, "y": 90.987654}, {"x": 85.123456, "y": 95.987654}]'),
('Zone_5', '[{"x": 90.123456, "y": 100.987654}, {"x": 95.123456, "y": 105.987654}, {"x": 100.123456, "y": 110.987654}, {"x": 105.123456, "y": 115.987654}]');