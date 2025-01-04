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